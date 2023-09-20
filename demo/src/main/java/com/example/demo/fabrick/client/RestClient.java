package com.example.demo.fabrick.client;

import com.example.demo.fabrick.dto.Response;
import com.example.demo.fabrick.dto.accountbalance.response.AccountBalanceResponse;
import com.example.demo.fabrick.dto.accounttransactions.response.AccountTransactions;
import com.example.demo.fabrick.dto.moneytransfer.request.MoneyTransferBody;
import com.example.demo.fabrick.dto.moneytransfer.response.MoneyTransferResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(RestClient.class);

    private RestTemplate restTemplate;

    public RestClient() {
        this.restTemplate = new RestTemplate();
    }

    public Response getCashAccountBalance(Long accountId) {
        Map<String, Long> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);
        HttpEntity<Void> requestEntity = new HttpEntity<>(addHeaders());

        ResponseEntity<AccountBalanceResponse> serviceResponse;
        Response response = new Response();
        try {
            logger.info("Sending cashAccountBalance request");
            serviceResponse = restTemplate.exchange(basePath + accountBalancePath, HttpMethod.GET, requestEntity, AccountBalanceResponse.class, uriParams);
            logger.info("Got response from cashAccountBalance service: " + serviceResponse.getBody());
            response.setStatusCode(HttpStatus.OK);
            response.setResponse(serviceResponse.getBody());
        } catch (HttpClientErrorException e) {
            logger.error("Got error from cashAccountBalance service: " + e.getMessage());
            AccountBalanceResponse res = e.getResponseBodyAs(AccountBalanceResponse.class);
            response.setResponse(res);
            response.setStatusCode(e.getStatusCode());
        } catch (Exception e) {
            logger.error("Got error from cashAccountBalance service: " + e.getMessage());
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setResponse(new AccountBalanceResponse());
        }
        return response;
    }

    public Response getAccountTransactions(Long accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate) {
        Map<String, Long> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(basePath + transactionsPath)
                .queryParam("fromAccountingDate", fromAccountingDate)
                .queryParam("toAccountingDate", toAccountingDate)
                .build();
        HttpEntity<Void> requestEntity = new HttpEntity<>(addHeaders());
        ResponseEntity<AccountTransactions> serviceResponse;
        Response response = new Response();
        try {
            logger.info("Sending accountTransactions request");
            serviceResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, AccountTransactions.class, uriParams);
            logger.info("Got response from accountTransactions service: " + serviceResponse.getBody());
            response.setStatusCode(HttpStatus.OK);
            response.setResponse(serviceResponse.getBody());
        } catch (HttpClientErrorException e) {
            logger.error("Got error from cashAccountBalance service: " + e.getMessage());
            AccountTransactions res = e.getResponseBodyAs(AccountTransactions.class);
            response.setResponse(res);
            response.setStatusCode(e.getStatusCode());
        } catch (Exception e) {
            logger.error("Got error from cashAccountBalance service: " + e.getMessage());
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setResponse(new AccountTransactions());
        }
        return response;
    }

    public Response moneyTransfer(Long accountId, MoneyTransferBody body) {
        Map<String, Long> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);
        HttpHeaders headers = addHeaders();
        headers.add("X-Time-Zone", "Europe/Rome");
        HttpEntity<MoneyTransferBody> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<MoneyTransferResponse> serviceResponse;
        Response response = new Response();
        try {
            logger.info("Sending moneyTransfer request");
            serviceResponse = restTemplate.exchange(basePath + moneyTransferPath, HttpMethod.POST, requestEntity, MoneyTransferResponse.class, uriParams);
            logger.info("Got response from moneyTransfer service: " + serviceResponse.getBody());
            response.setStatusCode(HttpStatus.OK);
            response.setResponse(serviceResponse.getBody());
        } catch (HttpClientErrorException e) {
            logger.error("Got error from cashAccountBalance service: " + e.getMessage());
            MoneyTransferResponse res = e.getResponseBodyAs(MoneyTransferResponse.class);
            response.setResponse(res);
            response.setStatusCode(e.getStatusCode());
        } catch (Exception e) {
            logger.error("Got error from cashAccountBalance service: " + e.getMessage());
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setResponse(new MoneyTransferResponse());
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
