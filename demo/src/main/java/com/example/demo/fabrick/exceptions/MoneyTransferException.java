package com.example.demo.fabrick.exceptions;

import com.example.demo.fabrick.dto.moneytransfer.response.MoneyTransferResponse;

public class MoneyTransferException extends Exception {

    private MoneyTransferResponse response;

    public MoneyTransferException(MoneyTransferResponse response) {
        this.response = response;
    }

    public MoneyTransferResponse getResponse() {
        return response;
    }
}
