package com.example.demo.fabrick.dto.moneytransfer.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class MoneyTransferResponse implements Serializable {

    @JsonProperty
    private String status;
    @JsonProperty
    private List<Error> errors;
    @JsonProperty
    private Payload payload;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "MoneyTransferResponse{" +
                "status='" + status + '\'' +
                ", errors=" + errors +
                ", payload=" + payload +
                '}';
    }
}
