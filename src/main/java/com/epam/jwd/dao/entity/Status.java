package com.epam.jwd.dao.entity;

import java.util.Arrays;

public enum Status {
    Coming(1), Finished(2), Started(3);

    private final Integer id;

    Status(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static Status getById(int id) {
        return Arrays.stream(Status.values())
                .filter(status -> status.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
