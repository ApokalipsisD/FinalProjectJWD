package com.epam.jwd.dao.entity;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMIN(1), TEACHER(2), STUDENT(3), USER(4);

    private final Integer id;

    Role(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static Role getById(int id) {
        return Arrays.stream(Role.values())
                .filter(role -> role.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static List<Role> valuesAsList(){
        return Arrays.asList(values());
    }
}
