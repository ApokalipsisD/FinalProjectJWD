package com.epam.jwd.dao.entity;

/**
 * Class Entity which provides id of provided type
 * @param <T> type of id
 */
public abstract class Entity<T> {
    /**
     * Field of generic type which provides id
     */
    protected T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
