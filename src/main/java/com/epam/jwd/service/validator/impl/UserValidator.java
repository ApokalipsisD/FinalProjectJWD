package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.util.Objects;

public class UserValidator implements Validator<UserDto, Integer> {

    private static final Integer MIN_LOGIN_LENGTH = 3;
    private static final Integer MAX_LOGIN_LENGTH = 20;
    private static final String LOGIN_PATTERN = "^[\\w.-]{3,20}[0-9a-zA-Z]$";

    private static final Integer MIN_PASSWORD_LENGTH = 8;
    private static final Integer MAX_PASSWORD_LENGTH = 20;
    private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";

    @Override
    public void validate(UserDto userDto) throws ServiceException {
        validateLogin(userDto.getLogin());
        validatePassword(userDto.getPassword());
    }

    private void validateLogin(String login) throws ServiceException {
        if (Objects.isNull(login)) {
            throw new ServiceException(MessageException.LOGIN_IS_NULL_EXCEPTION);
        }

        if(!login.matches(LOGIN_PATTERN)){
            throw new ServiceException(MessageException.INCORRECT_LOGIN_EXCEPTION);
        }

        if (login.length() < MIN_LOGIN_LENGTH
                || login.length() > MAX_LOGIN_LENGTH) {
            throw new ServiceException(MessageException.INCORRECT_LOGIN_LENGTH_EXCEPTION);
        }

    }

    private void validatePassword(String password) throws ServiceException {
        if (Objects.isNull(password)) {
            throw new ServiceException(MessageException.PASSWORD_IS_NULL_EXCEPTION);
        }

        if (password.length() < MIN_PASSWORD_LENGTH
                || password.length() > MAX_PASSWORD_LENGTH) {
            throw new ServiceException(MessageException.INCORRECT_PASSWORD_LENGTH_EXCEPTION);
        }

        if (!password.matches(PASSWORD_PATTERN)) {
            throw new ServiceException(MessageException.INCORRECT_PASSWORD_EXCEPTION);
        }
    }
}
