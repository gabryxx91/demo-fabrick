package com.example.demo.fabrick.controller;

import com.example.demo.fabrick.dto.AccountBalanceResponse;
import com.example.demo.fabrick.dto.AccountTransactions;
import com.example.demo.fabrick.service.FabrickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class FabrickController {

    @Autowired
    private FabrickService fabrickService;

    @GetMapping("/account/{accountId}/balance")
    public AccountBalanceResponse getCashAccountBalance(@PathVariable Long accountId) {
        return fabrickService.getCashAccountBalance(accountId);
    }

    @GetMapping("/account/{accountId}/transactions")
    public AccountTransactions getAccountTransactions(@PathVariable Long accountId, @RequestParam LocalDate fromAccountingDate,
                                                            @RequestParam LocalDate toAccountingDate) {
        return fabrickService.getAccountTransactions(accountId, fromAccountingDate, toAccountingDate);
    }


}
