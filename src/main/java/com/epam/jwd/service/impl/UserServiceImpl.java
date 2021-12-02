package com.epam.jwd.service.impl;

import com.epam.jwd.dao.entity.User;
import com.epam.jwd.dao.impl.UserDao;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.UserConverter;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.UserValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserServiceImpl implements Service<UserDto, Integer> {
    private final UserDao userDao = new UserDao();
    private final Validator<UserDto, Integer> validator = new UserValidator();
    private final Converter<User, UserDto, Integer> converter = new UserConverter();

    @Override
    public UserDto create(UserDto userDto) {
//        validator.validate(userDto);
        return converter.convert(userDao.save(converter.convert(userDto)));
    }

    @Override
    public boolean update(UserDto userDto) {
        validator.validate(userDto);
        return userDao.update(converter.convert(userDto));
    }

    @Override
    public boolean delete(UserDto userDto) {
        validator.validate(userDto);
        return userDao.delete(converter.convert(userDto));
    }

    @Override
    public UserDto getById(Integer id) {
        validator.validateId(id);
        User result = userDao.findById(id);
        if (Objects.isNull(result)) {
            throw new ServiceException(MessageException.USER_NOT_FOUND_EXCEPTION);
        }
        return converter.convert(result);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> userList = userDao.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        userList.forEach(user -> userDtoList.add(converter.convert(user)));
        return userDtoList;
    }

    public UserDto getByLogin(String login) {
        User result = userDao.findByLogin(login);
        if (Objects.isNull(result)) {
            throw new ServiceException(MessageException.USER_NOT_FOUND_EXCEPTION);
        }
        return converter.convert(result);
    }



    // getByLogin
    // sort
}