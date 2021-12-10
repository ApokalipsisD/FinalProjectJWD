package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.sql.Date;
import java.time.LocalDate;

public class AccountValidator implements Validator<AccountDto, Integer> {
    private static final Integer MIN_NAME_LENGTH = 2;
    private static final Integer MAX_FIRST_NAME_LENGTH = 20;
    private static final Integer MAX_LAST_NAME_LENGTH = 25;
    private static final String NAME_PATTERN = "^([А-Я][а-яё]{2,20}|[A-Z][a-z]{2,20})$";
    private static final String EMAIL_PATTERN = "^([a-zA-Z0-9_-]+\\.)*[a-zA-Z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    private static final String DATE_PATTERN = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";

    @Override
    public void validate(AccountDto value) throws ServiceException {
        validateId(value.getId());
        validateFirstName(value.getFirstName());
        if(value.getLastName()!=null){
            validateLastName(value.getLastName());
        }

        if(value.getEmail()!=null){
            validateEmail(value.getEmail());
        }
        validateDate(value.getBirthDate().toString());
//        validateRoleId(value.getRoleId());
//        validateUserId(value.getUserId());
    }

    private void validateFirstName(String firstName) throws ServiceException {
//        if (Objects.isNull(firstName)) {
//            throw new ServiceException(MessageException.FIRST_NAME_IS_NULL_EXCEPTION);
//        }
        if (!firstName.matches(NAME_PATTERN)) {
            throw new ServiceException(MessageException.INCORRECT_FIRST_NAME_EXCEPTION);
        }
        if (firstName.length() < MIN_NAME_LENGTH || firstName.length() > MAX_FIRST_NAME_LENGTH) {
            throw new ServiceException(MessageException.INCORRECT_FIRST_NAME_LENGTH_EXCEPTION);
        }
    }

    private void validateLastName(String lastName) throws ServiceException {
//        if (Objects.isNull(lastName)) {
//            throw new ServiceException(MessageException.LAST_NAME_IS_NULL_EXCEPTION);
//        }
        if (!lastName.matches(NAME_PATTERN)) {
            throw new ServiceException(MessageException.INCORRECT_LAST_NAME_EXCEPTION);
        }
        if (lastName.length() < MIN_NAME_LENGTH || lastName.length() > MAX_LAST_NAME_LENGTH) {
            throw new ServiceException(MessageException.INCORRECT_LAST_NAME_LENGTH_EXCEPTION);
        }
    }

    private void validateEmail(String email) throws ServiceException {
//        if (Objects.isNull(email)) {
//            throw new ServiceException(MessageException.EMAIL_IS_NULL_EXCEPTION);
//        }
        if (!email.matches(EMAIL_PATTERN)) {
            throw new ServiceException(MessageException.INCORRECT_EMAIL_EXCEPTION);
        }
    }

    public void validateDate(String birthDate) throws ServiceException {
//        if (Objects.isNull(birthDate)) {
//            throw new ServiceException(MessageException.BIRTH_DATE_IS_NULL_EXCEPTION);
//        }
        if (!birthDate.matches(DATE_PATTERN)
                || !Date.valueOf(birthDate).toLocalDate().isBefore(LocalDate.now())) {
            throw new ServiceException(MessageException.INCORRECT_DATE_EXCEPTION);
        }
    }

    private void validateRoleId(Integer id) {
//        if (Objects.isNull(id)) {
//            throw new ServiceException(MessageException.ROLE_ID_IS_NULL_EXCEPTION);
//        }
    }

    private void validateUserId(Integer id) {
//        if (Objects.isNull(id)) {
//            throw new ServiceException(MessageException.USER_ID_IS_NULL_EXCEPTION);
//        }
    }
}
