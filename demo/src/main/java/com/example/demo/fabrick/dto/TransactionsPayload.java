package com.example.demo.fabrick.dto;

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


}
