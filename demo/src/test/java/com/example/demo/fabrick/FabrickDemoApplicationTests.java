package com.example.demo.fabrick;

import com.example.demo.fabrick.client.RestClient;
import com.example.demo.fabrick.dto.Response;
import com.example.demo.fabrick.dto.accountbalance.response.AccountBalanceResponse;
import com.example.demo.fabrick.dto.accounttransactions.response.AccountTransactions;
import com.example.demo.fabrick.dto.moneytransfer.request.Account;
import com.example.demo.fabrick.dto.moneytransfer.request.Creditor;
import com.example.demo.fabrick.dto.moneytransfer.request.MoneyTransferBody;
import com.example.demo.fabrick.dto.moneytransfer.response.MoneyTransferResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
class FabrickDemoApplicationTests {

	Logger logger = LoggerFactory.getLogger(FabrickDemoApplicationTests.class);
	private static MoneyTransferBody moneyTransferBody;

	@Autowired
	private RestClient restClient;

	@Value("${fabrick.accountId}")
	private Long accountId;

	private static final String OK = "OK";
	private static final String KO = "KO";

	@BeforeAll
	static void setUp() {
		moneyTransferBody = new MoneyTransferBody();
		Creditor creditor = new Creditor();
		creditor.setName("John Doe");
		Account account = new Account();
		account.setAccountCode("IT23A0336844430152923804660");
		creditor.setAccount(account);
		moneyTransferBody.setCreditor(creditor);
		moneyTransferBody.setExecutionDate(LocalDate.of(2019, 4, 1));
		moneyTransferBody.setAmount(new BigDecimal(800));
		moneyTransferBody.setCurrency("EUR");
		moneyTransferBody.setDescription("Payment invoice 75/2017");
		moneyTransferBody.setFeeAccountId("45685475");

	}
	@Test
	void testAccountBalanceOK() {
		logger.info("Running testAccountBalanceOK");
		Response serviceResponse = restClient.getCashAccountBalance(accountId);
		AccountBalanceResponse response = (AccountBalanceResponse) serviceResponse.getResponse();
		Assertions.assertTrue(OK.equalsIgnoreCase(response.getStatus()));
	}

	@Test
	void testAccountBalanceKO() {
		logger.info("Running testAccountBalanceOK");
		Response serviceResponse = restClient.getCashAccountBalance(accountId);
		AccountBalanceResponse response = (AccountBalanceResponse) serviceResponse.getResponse();
		Assertions.assertTrue(KO.equalsIgnoreCase(response.getStatus()));
	}

	@Test
	void testAccountTransactionsOK() {
		logger.info("Running testAccountTransactionsOK");
		LocalDate from = LocalDate.of(2019, 1, 1);
		LocalDate to = LocalDate.of(2019, 12, 1);
		Response serviceResponse = restClient.getAccountTransactions(accountId, from, to);
		AccountTransactions response = (AccountTransactions) serviceResponse.getResponse();
		Assertions.assertTrue(OK.equalsIgnoreCase(response.getStatus()));
	}
	@Test
	void testAccountTransactionsKO() {
		logger.info("Running testAccountTransactionsOK");
		LocalDate from = LocalDate.of(2019, 1, 1);
		LocalDate to = LocalDate.of(2019, 12, 1);
		Response serviceResponse = restClient.getAccountTransactions(accountId, from, to);
		AccountTransactions response = (AccountTransactions) serviceResponse.getResponse();
		Assertions.assertTrue(KO.equalsIgnoreCase(response.getStatus()));
	}

	@Test
	void testMoneyTransferOK() {
		logger.info("Running testMoneyTransferOK");
		Response serviceResponse = restClient.moneyTransfer(accountId, moneyTransferBody);
		MoneyTransferResponse response = (MoneyTransferResponse) serviceResponse.getResponse();
		Assertions.assertTrue(OK.equalsIgnoreCase(response.getStatus()));
	}

	@Test
	void testMoneyTransferKO() {
		logger.info("Running testMoneyTransferKO");
		Response serviceResponse = restClient.moneyTransfer(accountId, moneyTransferBody);
		MoneyTransferResponse response = (MoneyTransferResponse) serviceResponse.getResponse();
		Assertions.assertTrue(KO.equalsIgnoreCase(response.getStatus()));
	}
}
