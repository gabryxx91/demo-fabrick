package com.example.demo.fabrick.dto.accountbalance.response;

import com.example.demo.fabrick.dto.Response;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class AccountBalanceResponse implements Serializable {

    @JsonProperty
    private String status;
    @JsonProperty
    private List<String> error;

    @JsonProperty
    private Payload payload;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "AccountBalanceResponse{" +
                "status='" + status + '\'' +
                ", error=" + error +
                ", payload=" + payload +
                '}';
    }
}
