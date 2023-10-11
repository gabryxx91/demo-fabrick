package com.example.demo.fabrick.controller;

import com.example.demo.fabrick.dto.Error;
import com.example.demo.fabrick.dto.accountbalance.response.AccountBalanceResponse;
import com.example.demo.fabrick.dto.accounttransactions.response.AccountTransactions;
import com.example.demo.fabrick.dto.moneytransfer.response.MoneyTransferResponse;
import com.example.demo.fabrick.exceptions.AccountBalanceResponseException;
import com.example.demo.fabrick.exceptions.AccountTransactionsException;
import com.example.demo.fabrick.exceptions.InvalidDateException;
import com.example.demo.fabrick.exceptions.MoneyTransferException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(InvalidDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<Error> onInvalidDateException(InvalidDateException e) {
        List<Error> errors = new ArrayList<>();
        Error error = new Error();
        error.setCode(e.getField());
        error.setDescription(e.getMessage());
        errors.add(error);
        return errors;
    }

    @ExceptionHandler(AccountBalanceResponseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<Error> onAccountBalanceResponseException(AccountBalanceResponseException e) {
        AccountBalanceResponse response = e.getResponse();
        return response.getErrors();
    }

    @ExceptionHandler(AccountTransactionsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<Error> onAccountTransactionsException(AccountTransactionsException e) {
        AccountTransactions response = e.getResponse();
        return response.getErrors();
    }

    @ExceptionHandler(MoneyTransferException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<Error> onMoneyTransferException(MoneyTransferException e) {
        MoneyTransferResponse response = e.getResponse();
        return response.getErrors();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    List<Error> onGenericException(Exception e) {
        List<Error> errors = new ArrayList<>();
        Error error = new Error();
        error.setDescription(e.getMessage());
        return errors;
    }
}
