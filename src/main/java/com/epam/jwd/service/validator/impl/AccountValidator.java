package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class AccountValidator implements Validator<AccountDto, Integer> {
    private static final Integer MIN_NAME_LENGTH = 2;
    private static final Integer MAX_FIRST_NAME_LENGTH = 20;
    private static final Integer MAX_LAST_NAME_LENGTH = 20;
    private static final Integer MIN_BIRTH_YEAR = 1900;
    private static final String NAME_PATTERN = "^([А-Я][а-яё]{2,20}|[A-Z][a-z]{2,20})$";
    private static final String EMAIL_PATTERN = "^([a-zA-Z0-9_-]+\\.)*[a-zA-Z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    private static final String DATE_PATTERN = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";

    @Override
    public void validate(AccountDto value) throws ServiceException {
        if (Objects.nonNull(value.getFirstName())) {
            validateFirstName(value.getFirstName());
        }

        if (Objects.nonNull(value.getLastName())) {
            validateLastName(value.getLastName());
        }

        if (Objects.nonNull(value.getEmail())) {
            validateEmail(value.getEmail());
        }

        if (Objects.nonNull(value.getBirthDate())) {
            validateDate(value.getBirthDate().toString());
        }
    }

    private void validateFirstName(String firstName) throws ServiceException {
        if (!firstName.matches(NAME_PATTERN)) {
            throw new ServiceException(MessageException.INCORRECT_FIRST_NAME_EXCEPTION);
        }
        if (firstName.length() < MIN_NAME_LENGTH || firstName.length() > MAX_FIRST_NAME_LENGTH) {
            throw new ServiceException(MessageException.INCORRECT_FIRST_NAME_LENGTH_EXCEPTION);
        }
    }

    private void validateLastName(String lastName) throws ServiceException {
        if (!lastName.matches(NAME_PATTERN)) {
            throw new ServiceException(MessageException.INCORRECT_LAST_NAME_EXCEPTION);
        }
        if (lastName.length() < MIN_NAME_LENGTH || lastName.length() > MAX_LAST_NAME_LENGTH) {
            throw new ServiceException(MessageException.INCORRECT_LAST_NAME_LENGTH_EXCEPTION);
        }
    }

    private void validateEmail(String email) throws ServiceException {
        if (!email.matches(EMAIL_PATTERN)) {
            throw new ServiceException(MessageException.INCORRECT_EMAIL_EXCEPTION);
        }
    }

    private void validateDate(String birthDate) throws ServiceException {
        if (!birthDate.matches(DATE_PATTERN)
                || !Date.valueOf(birthDate).toLocalDate().isBefore(LocalDate.now())
                || Date.valueOf(birthDate).toLocalDate().getYear() < MIN_BIRTH_YEAR) {
            throw new ServiceException(MessageException.INCORRECT_DATE_EXCEPTION);
        }
    }
}
