package com.example.demo.fabrick.controller;

import com.example.demo.fabrick.dto.accountbalance.response.AccountBalanceResponse;
import com.example.demo.fabrick.dto.accounttransactions.response.AccountTransactions;
import com.example.demo.fabrick.dto.moneytransfer.request.MoneyTransferBody;
import com.example.demo.fabrick.dto.moneytransfer.response.MoneyTransferResponse;
import com.example.demo.fabrick.exceptions.InvalidDateException;
import com.example.demo.fabrick.service.FabrickService;
import com.example.demo.fabrick.validation.DateValidator;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class FabrickController {

    Logger logger = LoggerFactory.getLogger(FabrickController.class);

    @Autowired
    private FabrickService fabrickService;

    @GetMapping("/accounts/{accountId}/balance")
    public AccountBalanceResponse getCashAccountBalance(@PathVariable Long accountId) throws Exception {
        logger.info("Invoked getCashAccountBalance service");
        return fabrickService.getCashAccountBalance(accountId);
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public AccountTransactions getAccountTransactions(@PathVariable Long accountId, @RequestParam LocalDate fromAccountingDate,
                                                      @RequestParam LocalDate toAccountingDate) throws Exception {
        logger.info("Invoked getAccountTransactions service");
        if (new DateValidator().validate(fromAccountingDate, toAccountingDate)) {
            throw new InvalidDateException("fromAccountingDate", "fromAccountingDate greater than toAccountingDate");
        }
        return fabrickService.getAccountTransactions(accountId, fromAccountingDate, toAccountingDate);
    }

    @PostMapping("accounts/{accountId}/payments/money-transfers")
    public MoneyTransferResponse moneyTransfer(@PathVariable Long accountId, @Valid @RequestBody MoneyTransferBody body) throws Exception {
        logger.info("Invoked moneyTransfer service");
        return fabrickService.moneyTransfer(accountId, body);
    }

}
