package com.example.demo.fabrick.client;

import com.example.demo.fabrick.dto.AccountBalanceResponse;
import com.example.demo.fabrick.dto.AccountTransactions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
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

    @Value("${fabrick.account.transactions}")
    private String transactionsPath;

    @Value("${fabrick.account.id}")
    private String accountId;

    @Value("${fabrick.auth.schema}")
    private String authSchema;

    @Value("${fabrick.api.key}")
    private String apiKey;

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private HttpStatus status;

    public RestClient() {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
    }

    public AccountBalanceResponse getCashAccountBalance(Long accountId) {
        Map<String, Long> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);
        addHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<AccountBalanceResponse> response = restTemplate.exchange(basePath + accountBalancePath, HttpMethod.GET, requestEntity, AccountBalanceResponse.class, uriParams);
        return response.getBody();
    }

    public AccountTransactions getAccountTransactions(Long accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate) {
        Map<String, Long> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(basePath + transactionsPath)
                .queryParam("fromAccountingDate", fromAccountingDate)
                .queryParam("toAccountingDate", toAccountingDate)
                .build();
        addHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<AccountTransactions> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, AccountTransactions.class, uriParams);
        return response.getBody();
    }

    private void addHeaders() {
        if (null == headers.get("Auth-Schema") && null == headers.get("apikey")) {
            headers.add("Auth-Schema", authSchema);
            headers.add("apikey", apiKey);
        }
    }
}
