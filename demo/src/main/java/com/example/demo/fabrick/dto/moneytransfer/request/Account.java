package com.example.demo.fabrick.dto.moneytransfer.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Account implements Serializable {

    @JsonProperty
    private String accountCode;

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }
}
