package com.example.demo.fabrick.dto.moneytransfer.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class MoneyTransferBody implements Serializable {

    @JsonProperty
    @NotNull
    @Valid
    private Creditor creditor;
    @JsonProperty
    @NotNull
    @Size(min = 1, max = 140)
    private String description;
    @JsonProperty
    @NotNull
    @Pattern(regexp = "^[A-Z]+$")
    private String currency;
    @JsonProperty
    @NotNull
    private BigDecimal amount;
    @JsonProperty
    private LocalDate executionDate;
    @JsonProperty
    private String feeAccountId;

    public Creditor getCreditor() {
        return creditor;
    }

    public void setCreditor(Creditor creditor) {
        this.creditor = creditor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(LocalDate executionDate) {
        this.executionDate = executionDate;
    }

    public String getFeeAccountId() {
        return feeAccountId;
    }

    public void setFeeAccountId(String feeAccountId) {
        this.feeAccountId = feeAccountId;
    }
}
