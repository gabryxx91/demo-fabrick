package com.example.demo.fabrick.service;

import com.example.demo.fabrick.client.RestClient;
import com.example.demo.fabrick.db.entities.Transaction;
import com.example.demo.fabrick.db.repository.TransactionRepository;
import com.example.demo.fabrick.dto.Response;
import com.example.demo.fabrick.dto.accounttransactions.response.AccountTransactions;
import com.example.demo.fabrick.dto.accounttransactions.response.Transactions;
import com.example.demo.fabrick.dto.moneytransfer.request.MoneyTransferBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FabrickService {

    Logger logger = LoggerFactory.getLogger(FabrickService.class);

    @Autowired
    private RestClient restClient;

    @Autowired
    private TransactionRepository transactionRepository;

    public Response getCashAccountBalance(Long accountId) {
        return restClient.getCashAccountBalance(accountId);
    }

    public Response getAccountTransactions(Long accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate) {
        Response response = restClient.getAccountTransactions(accountId, fromAccountingDate, toAccountingDate);
        AccountTransactions responseInternal = (AccountTransactions) response.getResponse();
        List<Transactions> transactionsList = responseInternal.getPayload().getList();
        if (null != transactionsList && !transactionsList.isEmpty()) {
            logger.info("Saving returned transactions on db");
            List<Transaction> dbList = new ArrayList<>();
            transactionsList.forEach(transaction -> {
                Transaction trx = new Transaction();
                BeanUtils.copyProperties(transaction, trx);
                trx.setEnumeration(transaction.getType().getEnumeration());
                trx.setValue(transaction.getType().getValue());
                dbList.add(trx);
            });
            transactionRepository.saveAll(dbList);
            logger.info("Saved " + transactionsList.size() + " transactions on db");
        }
        return response;
    }

    public Response moneyTransfer(Long accountId, MoneyTransferBody body) {
         return restClient.moneyTransfer(accountId, body);
    }
}
