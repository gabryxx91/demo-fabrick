package com.example.demo.fabrick.controller;

import com.example.demo.fabrick.dto.Response;
import com.example.demo.fabrick.dto.accountbalance.response.AccountBalanceResponse;
import com.example.demo.fabrick.dto.accounttransactions.response.AccountTransactions;
import com.example.demo.fabrick.dto.moneytransfer.request.MoneyTransferBody;
import com.example.demo.fabrick.dto.moneytransfer.response.MoneyTransferResponse;
import com.example.demo.fabrick.service.FabrickService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class FabrickController {

    Logger logger = LoggerFactory.getLogger(FabrickController.class);

    @Autowired
    private FabrickService fabrickService;

    @GetMapping("/accounts/{accountId}/balance")
    public ResponseEntity<AccountBalanceResponse> getCashAccountBalance(@PathVariable Long accountId) {
        logger.info("Invoked getCashAccountBalance service");
        Response response = fabrickService.getCashAccountBalance(accountId);
        return new ResponseEntity<>((AccountBalanceResponse) response.getResponse(), response.getStatusCode());
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<AccountTransactions> getAccountTransactions(@PathVariable Long accountId, @RequestParam LocalDate fromAccountingDate,
                                                            @RequestParam LocalDate toAccountingDate) {
        logger.info("Invoked getAccountTransactions service");
        Response response = fabrickService.getAccountTransactions(accountId, fromAccountingDate, toAccountingDate);
        return new ResponseEntity<>((AccountTransactions) response.getResponse(), response.getStatusCode());
    }

    @PostMapping("accounts/{accountId}/payments/money-transfers")
    public ResponseEntity<MoneyTransferResponse> moneyTransfer(@PathVariable Long accountId, @Valid @RequestBody MoneyTransferBody body) {
        logger.info("Invoked moneyTransfer service");
        Response response = fabrickService.moneyTransfer(accountId, body);
        return new ResponseEntity<>((MoneyTransferResponse) response.getResponse(), response.getStatusCode());
    }

}
