package com.example.demo.fabrick.client;

import com.example.demo.fabrick.dto.accountbalance.response.AccountBalanceResponse;
import com.example.demo.fabrick.dto.accounttransactions.response.AccountTransactions;
import com.example.demo.fabrick.dto.moneytransfer.request.MoneyTransferBody;
import com.example.demo.fabrick.dto.moneytransfer.response.MoneyTransferResponse;
import com.example.demo.fabrick.exceptions.AccountBalanceResponseException;
import com.example.demo.fabrick.exceptions.AccountTransactionsException;
import com.example.demo.fabrick.exceptions.MoneyTransferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    Logger logger = LoggerFactory.getLogger(RestClient.class);

    private RestTemplate restTemplate;

    public RestClient() {
        this.restTemplate = new RestTemplate();
    }

    public AccountBalanceResponse getCashAccountBalance(Long accountId) throws Exception {
        Map<String, Long> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);
        HttpEntity<Void> requestEntity = new HttpEntity<>(addHeaders());
        try {
            logger.info("Sending cashAccountBalance request");
            ResponseEntity<AccountBalanceResponse> serviceResponse = restTemplate.exchange(basePath + accountBalancePath, HttpMethod.GET, requestEntity, AccountBalanceResponse.class, uriParams);
            logger.info("Got response from cashAccountBalance service: {}", serviceResponse.getBody());
            return serviceResponse.getBody();
        } catch (HttpClientErrorException e) {
            logger.error("Got error from cashAccountBalance service: {}", e.getMessage());
            AccountBalanceResponse res = e.getResponseBodyAs(AccountBalanceResponse.class);
            throw new AccountBalanceResponseException(res);
        } catch (Exception e) {
            logger.error("Got error from cashAccountBalance service: {}", e.getMessage());
            throw e;
        }
    }

    public AccountTransactions getAccountTransactions(Long accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate) throws Exception {
        Map<String, Long> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(basePath + transactionsPath)
                .queryParam("fromAccountingDate", fromAccountingDate)
                .queryParam("toAccountingDate", toAccountingDate)
                .build();
        HttpEntity<Void> requestEntity = new HttpEntity<>(addHeaders());
        try {
            logger.info("Sending accountTransactions request");
            ResponseEntity<AccountTransactions> serviceResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, AccountTransactions.class, uriParams);
            logger.info("Got response from accountTransactions service: {}", serviceResponse.getBody());
            return serviceResponse.getBody();
        } catch (HttpClientErrorException e) {
            logger.error("Got error from cashAccountBalance service: {}", e.getMessage());
            AccountTransactions res = e.getResponseBodyAs(AccountTransactions.class);
            throw new AccountTransactionsException(res);
        } catch (Exception e) {
            logger.error("Got error from cashAccountBalance service: {}", e.getMessage());
            throw e;
        }
    }

    public MoneyTransferResponse moneyTransfer(Long accountId, MoneyTransferBody body) throws Exception {
        Map<String, Long> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);
        HttpHeaders headers = addHeaders();
        headers.add("X-Time-Zone", "Europe/Rome");
        HttpEntity<MoneyTransferBody> requestEntity = new HttpEntity<>(body, headers);
        try {
            logger.info("Sending moneyTransfer request");
            ResponseEntity<MoneyTransferResponse> serviceResponse = restTemplate.exchange(basePath + moneyTransferPath, HttpMethod.POST, requestEntity, MoneyTransferResponse.class, uriParams);
            logger.info("Got response from moneyTransfer service: {}", serviceResponse.getBody());
            return serviceResponse.getBody();
        } catch (HttpClientErrorException e) {
            logger.error("Got error from cashAccountBalance service: {}", e.getMessage());
            MoneyTransferResponse res = e.getResponseBodyAs(MoneyTransferResponse.class);
            throw new MoneyTransferException(res);
        } catch (Exception e) {
            logger.error("Got error from cashAccountBalance service: {}", e.getMessage());
            throw e;
        }
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
