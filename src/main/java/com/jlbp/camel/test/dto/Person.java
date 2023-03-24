package com.jlbp.camel.test.dto;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author josbanpe
 */
@Data
public class Person implements Serializable {

    private Long id;
    private String name;

}
