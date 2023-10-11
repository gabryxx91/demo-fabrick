package com.example.demo.fabrick.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Error implements Serializable {

    @JsonProperty
    private String code;
    @JsonProperty
    private String description;
    @JsonProperty
    private String params;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "Error{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", params='" + params + '\'' +
                '}';
    }
}
