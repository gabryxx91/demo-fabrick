package com.example.demo.fabrick.dto.accountbalance.response;

import com.example.demo.fabrick.dto.CommonFieldsResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AccountBalanceResponse extends CommonFieldsResponse implements Serializable {

    @JsonProperty
    private Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "AccountBalanceResponse{" +
                "payload=" + payload +
                '}';
    }
}
