package com.embraiz.service.common;

public class Message {
    private Integer code;
    private String mesage;
    private String fieldName;

    public Integer getCode() {
        return code;
    }
    public String getMesage(){
        return mesage;
    }
    public String getFieldName(){
        return fieldName;
    }
    public void setCode(Integer code){
        this.code = code;
    }
    public void setMesage(String  message){
        this.mesage = message;
    }
    public void setFieldNamee(String  fieldName){
        this.fieldName = fieldName;
    }
}
