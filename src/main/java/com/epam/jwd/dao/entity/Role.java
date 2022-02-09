package com.epam.jwd.dao.entity;

import java.util.Arrays;
import java.util.List;

/**
 * Enum which describes available roles in Course System
 */
public enum Role {
    ADMIN(1), TEACHER(2), STUDENT(3), USER(4), UNAUTHORIZED(5);

    /**
     * Integer field which describes role id
     */
    private final Integer id;

    /**
     * @param id - role id
     */
    Role(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    /**
     * Method for getting a role by id
     *
     * @param id - role id
     * @return - role with provided id, null if there is no role with provided id
     */
    public static Role getById(int id) {
        return Arrays.stream(Role.values())
                .filter(role -> role.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Method for getting list with all roles
     *
     * @return - list with all roles
     */
    public static List<Role> valuesAsList() {
        return Arrays.asList(values());
    }
}
