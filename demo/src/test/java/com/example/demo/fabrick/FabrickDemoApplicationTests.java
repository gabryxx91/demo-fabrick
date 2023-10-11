package com.example.demo.fabrick;

import com.example.demo.fabrick.client.RestClient;
import com.example.demo.fabrick.controller.FabrickController;
import com.example.demo.fabrick.dto.Error;
import com.example.demo.fabrick.dto.accountbalance.response.AccountBalanceResponse;
import com.example.demo.fabrick.dto.accountbalance.response.Payload;
import com.example.demo.fabrick.dto.accounttransactions.response.AccountTransactions;
import com.example.demo.fabrick.dto.accounttransactions.response.Transactions;
import com.example.demo.fabrick.dto.accounttransactions.response.TransactionsPayload;
import com.example.demo.fabrick.dto.accounttransactions.response.TypeEnum;
import com.example.demo.fabrick.dto.moneytransfer.request.Account;
import com.example.demo.fabrick.dto.moneytransfer.request.Creditor;
import com.example.demo.fabrick.dto.moneytransfer.request.MoneyTransferBody;
import com.example.demo.fabrick.dto.moneytransfer.response.MoneyTransferResponse;
import com.example.demo.fabrick.exceptions.AccountBalanceResponseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FabrickDemoApplicationTests {

	Logger logger = LoggerFactory.getLogger(FabrickDemoApplicationTests.class);

	@Autowired
	private FabrickController fabrickController;

	@MockBean
	private RestClient restClient;

	@Value("${fabrick.accountId}")
	private static Long accountId;

	private static MoneyTransferBody moneyTransferBody;

	private static MoneyTransferResponse moneyTransferResponseMocked;

	private static AccountTransactions accountTransactionsResponseMocked;

	private static final String OK = "OK";
	private static final String KO = "KO";

	@BeforeAll
	public static void setUp() throws Exception {

		accountTransactionsResponseMocked = createAccountTransactionResponse();

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

		moneyTransferResponseMocked = new MoneyTransferResponse();
		moneyTransferResponseMocked.setStatus(OK);
		moneyTransferResponseMocked.setErrors(null);
		com.example.demo.fabrick.dto.moneytransfer.response.Payload payload = new com.example.demo.fabrick.dto.moneytransfer.response.Payload();
		payload.setMoneyTransferId("12345");
		moneyTransferResponseMocked.setPayload(payload);

	}
	@Test
	void testAccountBalanceOK() throws Exception {
		logger.info("Running testAccountBalanceOK");
		AccountBalanceResponse accountBalanceResponse = new AccountBalanceResponse();
		accountBalanceResponse.setStatus(OK);
		accountBalanceResponse.setErrors(null);
		Payload payload = new Payload();
		payload.setBalance(new BigDecimal("-19.41"));
		payload.setCurrency("EUR");
		payload.setAvailableBalance(new BigDecimal("-19.41"));
		payload.setDate(LocalDate.of(2023, 10, 11));
		accountBalanceResponse.setPayload(payload);
		Mockito.when(restClient.getCashAccountBalance(accountId)).thenReturn(accountBalanceResponse);
		AccountBalanceResponse response = fabrickController.getCashAccountBalance(accountId);
		Assertions.assertTrue(OK.equalsIgnoreCase(response.getStatus()));
	}

	@Test
	void testAccountBalanceKO() throws Exception {
		logger.info("Running testAccountBalanceKO");
		AccountBalanceResponse accountBalanceResponse = new AccountBalanceResponse();
		accountBalanceResponse.setStatus(KO);
		List<Error> errors = new ArrayList<>();
		Error error = new Error();
		error.setCode("REQ004");
		error.setDescription("Invalid account identifier");
		errors.add(error);
		accountBalanceResponse.setErrors(errors);
		accountBalanceResponse.setPayload(null);
		AccountBalanceResponseException e = new AccountBalanceResponseException(accountBalanceResponse);
		Mockito.when(restClient.getCashAccountBalance(Long.getLong("14537781"))).thenThrow(e);
		try {
			fabrickController.getCashAccountBalance(Long.getLong("14537781"));
		} catch (AccountBalanceResponseException ex) {
			Assertions.assertEquals("REQ004", ex.getResponse().getErrors().get(0).getCode());
		}
	}

	@Test
	void testAccountTransactionsOK() throws Exception {
		logger.info("Running testAccountTransactionsOK");
		LocalDate from = LocalDate.of(2019, 1, 1);
		LocalDate to = LocalDate.of(2019, 12, 1);
		Mockito.when(restClient.getAccountTransactions(accountId, from, to)).thenReturn(accountTransactionsResponseMocked);
		AccountTransactions response = fabrickController.getAccountTransactions(accountId, from, to);
		Assertions.assertEquals(2, response.getPayload().getList().size());
	}

	private static AccountTransactions createAccountTransactionResponse() {
		AccountTransactions response = new AccountTransactions();
		response.setStatus(OK);
		response.setErrors(null);
		TransactionsPayload transactionsPayload = new TransactionsPayload();
		transactionsPayload.setList(new ArrayList<>());
		Transactions transaction1 = new Transactions();
		transaction1.setTransactionId("282831");
		transaction1.setOperationId("00000000282831");
		transaction1.setAccountingDate(LocalDate.of(2019, 11, 29));
		transaction1.setValueDate(LocalDate.of(2019, 12, 1));
		transaction1.setAmount(new BigDecimal("-343.77"));
		transaction1.setCurrency("EUR");
		transaction1.setDescription("PD VISA CORPORATE 10");
		TypeEnum typeEnum = new TypeEnum();
		typeEnum.setEnumeration("GBS_TRANSACTION_TYPE");
		typeEnum.setValue("GBS_ACCOUNT_TRANSACTION_TYPE_0050");
		transaction1.setType(typeEnum);
		transactionsPayload.getList().add(transaction1);

		Transactions transaction2 = new Transactions();
		transaction2.setTransactionId("1460159524001");
		transaction2.setOperationId("19000191134336");
		transaction2.setAccountingDate(LocalDate.of(2019, 11, 11));
		transaction2.setValueDate(LocalDate.of(2019, 11, 9));
		transaction2.setAmount(new BigDecimal("854.00"));
		transaction2.setCurrency("EUR");
		transaction2.setDescription("BD LUCA TERRIBILE        DA 03268.49130         DATA ORDINE 09112019 COPERTURA VISA");
		TypeEnum typeEnum2 = new TypeEnum();
		typeEnum2.setEnumeration("GBS_TRANSACTION_TYPE");
		typeEnum2.setValue("GBS_ACCOUNT_TRANSACTION_TYPE_0010");
		transaction2.setType(typeEnum2);
		transactionsPayload.getList().add(transaction2);

		response.setPayload(transactionsPayload);
		return response;
	}

	@Test
	void testAccountTransactionsKO() throws Exception {
		logger.info("Running testAccountTransactionsKO");
		LocalDate from = LocalDate.of(2019, 1, 1);
		LocalDate to = LocalDate.of(2019, 12, 1);
		Mockito.when(restClient.getAccountTransactions(accountId, from, to)).thenReturn(accountTransactionsResponseMocked);
		AccountTransactions response = fabrickController.getAccountTransactions(accountId, from, to);
		Assertions.assertNotEquals(3, response.getPayload().getList().size());
	}

	@Test
	void testMoneyTransferOK() throws Exception {
		logger.info("Running testMoneyTransferOK");
		Mockito.when(restClient.moneyTransfer(accountId, moneyTransferBody)).thenReturn(moneyTransferResponseMocked);
		MoneyTransferResponse response = fabrickController.moneyTransfer(accountId, moneyTransferBody);
		Assertions.assertEquals("12345", response.getPayload().getMoneyTransferId());
	}

	@Test
	void testMoneyTransferKO() throws Exception {
		logger.info("Running testMoneyTransferKO");
		Mockito.when(restClient.moneyTransfer(accountId, moneyTransferBody)).thenReturn(moneyTransferResponseMocked);
		MoneyTransferResponse response = fabrickController.moneyTransfer(accountId, moneyTransferBody);
		Assertions.assertNotEquals("1234", response.getPayload().getMoneyTransferId());
	}

}
