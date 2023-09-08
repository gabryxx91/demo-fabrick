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
}
