package com.embraiz.entity.base;

public class Max {
    private String name;
    private String asName;

    public Max(String name) {
        this.name = name;
        this.asName = name;
    }

    public Max as(String asName) {
        this.asName = asName;

        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAsName() {
        return asName;
    }

    public void setAsName(String asName) {
        this.asName = asName;
    }
}
