package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.entity.User;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.UserDto;

public class UserConverter implements Converter<User, UserDto, Integer> {
    @Override
    public User convert(UserDto userDto) {
        return new User(userDto.getId(),
                userDto.getLogin(),
                userDto.getPassword());
    }

    @Override
    public UserDto convert(User user) {
        return new UserDto(user.getId(),
                user.getLogin(),
                user.getPassword());
    }
}
