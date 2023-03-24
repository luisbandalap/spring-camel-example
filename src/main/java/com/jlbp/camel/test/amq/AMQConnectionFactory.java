package com.jlbp.camel.test.amq;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.Arrays;

/**
 *
 * @author josbanpe
 */
@Configuration
public class AMQConnectionFactory {

    @Value("${jsa.activemq.broker.url}")
    String brokerUrl;
    @Value("${jsa.activemq.broker.username}")
    String userName;
    @Value("${jsa.activemq.broker.password}")
    String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUserName(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setConnectionIDPrefix("spring-camel-example");
        connectionFactory.setTrustedPackages(Arrays.asList("java.util"));
        return connectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jsaFactory(final ConnectionFactory connectionFactory,
                                                         final DefaultJmsListenerContainerFactoryConfigurer configurer) {
        final DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConcurrency("5-15");
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(final ConnectionFactory connectionFactory) {
        final JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

}
