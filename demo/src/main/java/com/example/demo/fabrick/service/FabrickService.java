package com.example.demo.fabrick.service;

import com.example.demo.fabrick.client.RestClient;
import com.example.demo.fabrick.dto.accountbalance.response.AccountBalanceResponse;
import com.example.demo.fabrick.dto.accounttransactions.response.AccountTransactions;
import com.example.demo.fabrick.dto.moneytransfer.request.MoneyTransferBody;
import com.example.demo.fabrick.dto.moneytransfer.response.MoneyTransferResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FabrickService {

    @Autowired
    private RestClient restClient;

    public ResponseEntity<AccountBalanceResponse> getCashAccountBalance(Long accountId) {
        return restClient.getCashAccountBalance(accountId);
    }

    public ResponseEntity<AccountTransactions> getAccountTransactions(Long accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate) {
        return restClient.getAccountTransactions(accountId, fromAccountingDate, toAccountingDate);
    }

    public ResponseEntity<MoneyTransferResponse> moneyTransfer(Long accountId, MoneyTransferBody body) {
         return restClient.moneyTransfer(accountId, body);
    }
}
