package com.example.demo.fabrick.dto.accounttransactions.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class AccountTransactions implements Serializable {

    @JsonProperty
    private String status;
    @JsonProperty
    private List<String> error;

    @JsonProperty
    private TransactionsPayload payload;

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

    public TransactionsPayload getPayload() {
        return payload;
    }

    public void setPayload(TransactionsPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "AccountTransactions{" +
                "status='" + status + '\'' +
                ", error=" + error +
                ", payload=" + payload +
                '}';
    }
}
