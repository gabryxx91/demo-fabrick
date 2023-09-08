package com.example.demo.fabrick.client;

import com.example.demo.fabrick.dto.accountbalance.response.AccountBalanceResponse;
import com.example.demo.fabrick.dto.accounttransactions.response.AccountTransactions;
import com.example.demo.fabrick.dto.moneytransfer.request.MoneyTransferBody;
import com.example.demo.fabrick.dto.moneytransfer.response.MoneyTransferResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class RestClient {

    @Value("${fabrick.base.path}")
    private String basePath;

    @Value("${fabrick.account.balance.path}")
    private String accountBalancePath;

    @Value("${fabrick.account.transactions.path}")
    private String transactionsPath;

    @Value("${fabrick.money.transfer.path}")
    private String moneyTransferPath;

    @Value("${fabrick.auth.schema}")
    private String authSchema;

    @Value("${fabrick.api.key}")
    private String apiKey;

    private RestTemplate restTemplate;

    public RestClient() {
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<AccountBalanceResponse> getCashAccountBalance(Long accountId) {
        Map<String, Long> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);
        HttpEntity<Void> requestEntity = new HttpEntity<>(addHeaders());
        ResponseEntity<AccountBalanceResponse> response;
        try {
            response = restTemplate.exchange(basePath + accountBalancePath, HttpMethod.GET, requestEntity, AccountBalanceResponse.class, uriParams);
        } catch (HttpClientErrorException e) {
            AccountBalanceResponse res = e.getResponseBodyAs(AccountBalanceResponse.class);
            return new ResponseEntity<>(res, e.getStatusCode());
        }
        return response;
    }

    public ResponseEntity<AccountTransactions> getAccountTransactions(Long accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate) {
        Map<String, Long> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(basePath + transactionsPath)
                .queryParam("fromAccountingDate", fromAccountingDate)
                .queryParam("toAccountingDate", toAccountingDate)
                .build();
        HttpEntity<Void> requestEntity = new HttpEntity<>(addHeaders());
        ResponseEntity<AccountTransactions> response;
        try {
            response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, AccountTransactions.class, uriParams);
        } catch (HttpClientErrorException e) {
            AccountTransactions res = e.getResponseBodyAs(AccountTransactions.class);
            return new ResponseEntity<>(res, e.getStatusCode());
        }
        return response;
    }

    public ResponseEntity<MoneyTransferResponse> moneyTransfer(Long accountId, MoneyTransferBody body) {
        Map<String, Long> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);
        HttpHeaders headers = addHeaders();
        headers.add("X-Time-Zone", "Europe/Rome");
        HttpEntity<MoneyTransferBody> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<MoneyTransferResponse> response;
        try {
            response = restTemplate.exchange(basePath + moneyTransferPath, HttpMethod.POST, requestEntity, MoneyTransferResponse.class, uriParams);
        } catch (HttpClientErrorException e) {
            MoneyTransferResponse res = e.getResponseBodyAs(MoneyTransferResponse.class);
            return new ResponseEntity<>(res, e.getStatusCode());
        }
        return response;
    }

    private HttpHeaders addHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
        headers.add("Auth-Schema", authSchema);
        headers.add("apikey", apiKey);
        return headers;
    }

}
