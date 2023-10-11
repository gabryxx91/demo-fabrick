package com.example.demo.fabrick.exceptions;

import com.example.demo.fabrick.dto.accountbalance.response.AccountBalanceResponse;

public class AccountBalanceResponseException extends Exception {

    private AccountBalanceResponse response;

    public AccountBalanceResponseException(AccountBalanceResponse response) {
        this.response = response;
    }

    public AccountBalanceResponse getResponse() {
        return response;
    }
}
