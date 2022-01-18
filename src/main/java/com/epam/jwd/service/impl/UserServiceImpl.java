package com.epam.jwd.service.impl;

import com.epam.jwd.dao.entity.User;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.UserDao;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.UserConverter;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.UserValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserServiceImpl implements Service<UserDto, Integer> {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao userDao = new UserDao();
    private final Validator<UserDto, Integer> validator = new UserValidator();
    private final Converter<User, UserDto, Integer> converter = new UserConverter();

    @Override
    public UserDto create(UserDto userDto) throws ServiceException {
        validator.validate(userDto);
        try {
            return converter.convert(userDao.save(converter.convert(userDto)));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean update(UserDto userDto) throws ServiceException {
        validator.validate(userDto);
        try {
            return userDao.update(converter.convert(userDto));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean delete(UserDto userDto) throws ServiceException {
        validator.validate(userDto);
        try {
            return userDao.delete(converter.convert(userDto));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public UserDto getById(Integer id) throws ServiceException {
        validator.validateId(id);
        User result;
        try {
            result = userDao.findById(id);
            if (Objects.isNull(result)) {
                throw new DaoException(MessageException.USER_NOT_FOUND_EXCEPTION);
            }
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
        return converter.convert(result);
    }

    @Override
    public List<UserDto> getAll() throws ServiceException {
        List<User> userList;
        List<UserDto> userDtoList = new ArrayList<>();
        try {
            userList = userDao.findAll();
            userList.forEach(user -> userDtoList.add(converter.convert(user)));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }

        return userDtoList;
    }

    public UserDto getByLogin(String login) throws ServiceException {
        User result;
        try {
            result = userDao.findByLogin(login);
            if (Objects.isNull(result)) {
                throw new DaoException(MessageException.USER_NOT_FOUND_EXCEPTION);
            }
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
        return converter.convert(result);
    }


    public boolean checkIfLoginFree(String login) throws ServiceException {
        try {
            return userDao.checkIfLoginFree(login);
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    public boolean checkRepeatPassword(String password, String repeatPassword){
        return password.equals(repeatPassword);
    }

    // sort
}