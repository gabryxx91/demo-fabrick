package com.example.demo.fabrick.controller;

import com.example.demo.fabrick.dto.accountbalance.response.AccountBalanceResponse;
import com.example.demo.fabrick.dto.accounttransactions.response.AccountTransactions;
import com.example.demo.fabrick.dto.moneytransfer.request.MoneyTransferBody;
import com.example.demo.fabrick.dto.moneytransfer.response.MoneyTransferResponse;
import com.example.demo.fabrick.service.FabrickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class FabrickController {

    @Autowired
    private FabrickService fabrickService;

    @GetMapping("/accounts/{accountId}/balance")
    public ResponseEntity<AccountBalanceResponse> getCashAccountBalance(@PathVariable Long accountId) {
        return fabrickService.getCashAccountBalance(accountId);
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<AccountTransactions> getAccountTransactions(@PathVariable Long accountId, @RequestParam LocalDate fromAccountingDate,
                                                            @RequestParam LocalDate toAccountingDate) {
        return fabrickService.getAccountTransactions(accountId, fromAccountingDate, toAccountingDate);
    }

    @PostMapping("accounts/{accountId}/payments/money-transfers")
    public ResponseEntity<MoneyTransferResponse> moneyTransfer(@PathVariable Long accountId, @RequestBody MoneyTransferBody body) {
        return fabrickService.moneyTransfer(accountId, body);
    }

}
