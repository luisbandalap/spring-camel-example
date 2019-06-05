package com.jlbp.camel.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author josbanpe
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.jlbp.camel.test")
public class AppBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(AppBootstrap.class, args);
    }
    
}
