package com.example.demo.fabrick.dto.accounttransactions.response;

import java.io.Serializable;

public class TypeEnum implements Serializable {

    private String enumeration;
    private String value;

    public String getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(String enumeration) {
        this.enumeration = enumeration;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
