package com.example.demo.fabrick.validation;

import java.time.LocalDate;

public class DateValidator {

    public boolean validate(LocalDate fromAccountingDate, LocalDate toAccountingDate) {
        return fromAccountingDate.isAfter(toAccountingDate);
    }
}
