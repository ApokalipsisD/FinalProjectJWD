package com.epam.jwd.service.validator.api;

import com.epam.jwd.service.dto.AbstractDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;

import java.util.Objects;

/**
 *
 * @param <T> - abstract dto that converter will operate
 * @param <K> - type of id
 */
public interface Validator<T extends AbstractDto<K>, K> {
    /**
     * @param value - value to be validated
     * @throws ServiceException - if validation fails
     */
    void validate(T value) throws ServiceException;

    /**
     * @param value - id to be validated
     * @throws ServiceException - if value is incorrect
     */
    default void validateId(K value) throws ServiceException {
        if (Objects.isNull(value)) {
            throw new ServiceException(MessageException.ID_IS_NULL_EXCEPTION);
        }
        if ((Integer) value < 1) {
            throw new ServiceException(MessageException.INCORRECT_ID_EXCEPTION);
        }
    }
}
