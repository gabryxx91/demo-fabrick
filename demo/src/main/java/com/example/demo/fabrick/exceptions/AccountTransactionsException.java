package com.example.demo.fabrick.exceptions;

import com.example.demo.fabrick.dto.accounttransactions.response.AccountTransactions;

public class AccountTransactionsException extends Exception {

    private AccountTransactions response;

    public AccountTransactionsException(AccountTransactions response) {
        this.response = response;
    }

    public AccountTransactions getResponse() {
        return response;
    }
}
