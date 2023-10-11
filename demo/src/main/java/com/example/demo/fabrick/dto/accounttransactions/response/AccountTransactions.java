package com.example.demo.fabrick.dto.accounttransactions.response;

import com.example.demo.fabrick.dto.CommonFieldsResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AccountTransactions extends CommonFieldsResponse implements Serializable {
    @JsonProperty
    private TransactionsPayload payload;

    public TransactionsPayload getPayload() {
        return payload;
    }

    public void setPayload(TransactionsPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "AccountTransactions{" +
                "payload=" + payload +
                '}';
    }
}
