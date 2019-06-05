package com.jlbp.camel.test;

import com.google.gson.Gson;
import com.jlbp.camel.test.dto.Person;
import com.jlbp.camel.test.rest.ExampleService;
import javax.ws.rs.core.MediaType;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author josbanpe
 */
@Component
public class AppRouteBuilder extends RouteBuilder {

    @Value("${server.port}")
    String serverPort;

    @Override
    public void configure() throws Exception {
        CamelContext context = new DefaultCamelContext();
        
        // Configuramos el API REST
        restConfiguration()
                .port(serverPort)
                .enableCORS(true)
                .contextPath("/camel/") //Seteamos el Context PATH (solo sirve para generar el Swagger)
                .apiContextPath("/api-docs") // Ultima parte del PATH del swagger
                .apiProperty("api.title", "Test REST API")
                .apiProperty("api.version", "v1")
                .apiProperty("cors", "true") // cross-site
                .apiContextRouteId("doc-api")
                .component("servlet")
                .bindingMode(RestBindingMode.json);
        
        // Se crea un Endpoint REST, cualquier mensaje mandado aqui requiere una respuesta
        rest("/api/").description("Test REST Service")
                .id("api-route")
                .post("/bean")
                .produces(MediaType.APPLICATION_JSON)
                .consumes(MediaType.APPLICATION_JSON)
                .bindingMode(RestBindingMode.auto)
                .type(Person.class)
                .enableCORS(true)
                .to("direct:remoteService");

        from("direct:remoteService")
                .routeId("direct-route")
                .tracing()
                .log(">>> ${body}")
                
                .process((Exchange exchange) -> { // Procesamos el mensaje!
                    Person bodyMsg = ((Person) exchange.getIn().getBody()).clone();
                    ExampleService.example(bodyMsg);
                    exchange.getOut().setBody(bodyMsg); //Aqui podemos mandar el out
                })
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
                .wireTap("direct:testMessage"); // Enviamos una copia del mensaje original;
        
        from("direct:testMessage")
                .routeId("jms-test")
                .tracing()
                .process((Exchange exchange) -> {
                    exchange.getOut().setBody(new Gson().toJson(exchange.getOut().getBody()));
                })
                .inOnly("activemq:queue:com.jlbp.test-queue");
    }

}
