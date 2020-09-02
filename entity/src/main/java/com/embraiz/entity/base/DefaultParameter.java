package com.embraiz.entity.base;

public class DefaultParameter {
    private String name;
    private Object value;

    public DefaultParameter() {
    }

    public DefaultParameter(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
