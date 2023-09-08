package com.example.demo.fabrick.dto.moneytransfer.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Payload implements Serializable {

    @JsonProperty
    private String moneyTransferId;

    public String getMoneyTransferId() {
        return moneyTransferId;
    }

    public void setMoneyTransferId(String moneyTransferId) {
        this.moneyTransferId = moneyTransferId;
    }
}
