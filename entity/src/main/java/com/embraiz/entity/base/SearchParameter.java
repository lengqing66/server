package com.embraiz.entity.base;

@SuppressWarnings("unused")
public class SearchParameter extends DefaultParameter {
    private String type;

    public SearchParameter() {
    }

    public SearchParameter(String name, Object value, String type) {
        super(name, value);
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
