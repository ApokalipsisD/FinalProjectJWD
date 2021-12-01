package com.epam.jwd.service.converter.api;

import com.epam.jwd.dao.entity.Entity;
import com.epam.jwd.service.dto.AbstractDto;

public interface Converter<T extends Entity<K>, V extends AbstractDto<K>, K> {
    T convert(V entityDto);

    V convert(T entity);
}
