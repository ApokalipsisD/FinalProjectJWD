package com.epam.jwd.service.converter.api;

import com.epam.jwd.dao.entity.Entity;
import com.epam.jwd.service.dto.AbstractDto;

public interface Converter<T extends Entity<K>, V extends AbstractDto<K>, K> {
    /**
     * convert from DTO to DAO
     *
     * @param entityDto - DTO
     * @return - DAO
     */
    T convert(V entityDto);

    /**
     * convert from DAO to DTO
     *
     * @param entity - DAO
     * @return - DTO
     */
    V convert(T entity);
}
