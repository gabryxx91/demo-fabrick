package com.example.demo.fabrick.dto.moneytransfer.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class Creditor implements Serializable {

    @JsonProperty
    @NotNull
    @Size(min = 1, max = 70)
    private String name;

    @JsonProperty
    @NotNull
    @Valid
    private Account account;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
