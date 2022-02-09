package com.epam.jwd.dao.entity;

import java.util.Arrays;

/**
 * Enum which describes available status for courses in Course System
 */
public enum Status {
    Coming(1), Started(2), Finished(3);

    /**
     * Integer field which describes status id
     */
    private final Integer id;

    /**
     * @param id - status id
     */
    Status(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    /**
     * Method for getting a status by id
     *
     * @param id - status id
     * @return - status with provided id, null if there is no status with provided id
     */
    public static Status getById(int id) {
        return Arrays.stream(Status.values())
                .filter(status -> status.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
