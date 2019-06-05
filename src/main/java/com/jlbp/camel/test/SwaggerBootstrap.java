package com.jlbp.camel.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author josbanpe
 */
@Configuration
@EnableSwagger2
@Primary
//@Profile({"dev", "test", "qa"})
public class SwaggerBootstrap implements SwaggerResourcesProvider {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalDateTime.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class);
    }

    @Override
    public List<SwaggerResource> get() {
        //Api Camel
        SwaggerResource swaggerCamel = new SwaggerResource();
        swaggerCamel.setLocation("/camel/api-docs");
        swaggerCamel.setName("Camel Route API");
        
        //Api Springfox
        SwaggerResource swaggerDefault = new SwaggerResource();
        swaggerDefault.setLocation("/v2/api-docs");
        swaggerDefault.setName("Default API");
        
        
        return Arrays.asList(swaggerCamel, swaggerDefault);
    }
}
