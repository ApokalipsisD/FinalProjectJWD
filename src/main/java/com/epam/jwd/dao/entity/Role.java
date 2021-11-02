package com.epam.jwd.dao.entity;

public enum Role {
    ADMIN(1), TEACHER(2), STUDENT(3), USER(4);

    private final Integer id;

    Role(int id) {
        this.id = id;
    }

    //todo getById
}
