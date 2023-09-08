package com.example.demo.fabrick.service;

import com.example.demo.fabrick.client.RestClient;
import com.example.demo.fabrick.dto.AccountBalanceResponse;
import com.example.demo.fabrick.dto.AccountTransactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FabrickService {

    @Autowired
    private RestClient restClient;

    public AccountBalanceResponse getCashAccountBalance(Long accountId) {
        return restClient.getCashAccountBalance(accountId);
    }

    public AccountTransactions getAccountTransactions(Long accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate) {
        return restClient.getAccountTransactions(accountId, fromAccountingDate, toAccountingDate);
    }
}
