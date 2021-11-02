package com.epam.jwd.dao.entity;

public abstract class Entity<T> {
    protected T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
