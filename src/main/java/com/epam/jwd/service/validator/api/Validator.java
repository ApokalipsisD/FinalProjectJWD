package com.epam.jwd.service.validator.api;

import com.epam.jwd.service.dto.AbstractDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;

import java.util.Objects;

public interface Validator<T extends AbstractDto<K>, K> {
    void validate(T value);

    default void validateId(K value) {
        if (Objects.isNull(value)) {
            throw new ServiceException(MessageException.ID_IS_NULL_EXCEPTION);
        }
    }
}
