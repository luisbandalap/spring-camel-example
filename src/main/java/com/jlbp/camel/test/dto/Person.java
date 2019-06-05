package com.jlbp.camel.test.dto;

import java.io.Serializable;

/**
 *
 * @author josbanpe
 */
public class Person implements Serializable, Cloneable {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString(){
        return String.format("{id: %s, name: %s}", id, name);
    }
    
    @Override
    public Person clone()
    {
        Person myObj = new Person();
        myObj.id = this.id;
        myObj.name = this.name;
        return myObj; 
    }
}
