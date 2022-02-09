package com.epam.jwd.service.dto;

/**
 * Class AbstractDto which provides id of provided type
 * @param <T> type of id
 */
public abstract class AbstractDto<T> {
    protected T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
