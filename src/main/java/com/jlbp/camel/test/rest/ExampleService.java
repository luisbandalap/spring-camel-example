package com.jlbp.camel.test.rest;

import com.jlbp.camel.test.dto.Person;

/**
 *
 * @author josbanpe
 */
public class ExampleService {
    public static void example(Person bodyIn) {
        bodyIn.setName( "Hello, " + bodyIn.getName() );
        bodyIn.setId(bodyIn.getId() * 10);
    }
}
