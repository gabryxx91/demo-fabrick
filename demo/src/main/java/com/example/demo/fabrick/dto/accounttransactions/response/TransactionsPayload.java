package com.example.demo.fabrick.dto.accounttransactions.response;

import com.example.demo.fabrick.dto.accounttransactions.response.Transactions;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class TransactionsPayload implements Serializable {

    @JsonProperty
    private List<Transactions> list;

    public List<Transactions> getList() {
        return list;
    }

    public void setList(List<Transactions> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "TransactionsPayload{" +
                "list=" + list +
                '}';
    }
}
