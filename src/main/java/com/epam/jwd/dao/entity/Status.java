package com.epam.jwd.dao.entity;

public enum Status {
    Coming(1), Started(2), Finished(3);

    private final Integer id;

    Status(int id) {
        this.id = id;
    }
}
