package com.epam.jwd.dao.entity;

import java.sql.Date;
import java.util.Objects;

/**
 * Account entity class which extends Entity class with Integer id field
 */
public class Account extends Entity<Integer> {
    /**
     * String field with account's first name
     */
    private String firstName;
    /**
     * String field with account's last name
     */
    private String lastName;
    /**
     * String field with account's email
     */
    private String email;
    /**
     * Date field with account's birthdate
     */
    private Date birthDate;
    /**
     * Role field with account's role
     *
     * @see Role
     */
    private Role role;
    /**
     * Integer field with account's id which associated with current user
     */
    private Integer userId;

    /**
     * Constructor without arguments for creating empty Account object
     *
     * @see Account#Account(Integer)
     * @see Account#Account(String, String, String, Date, Integer, Integer)
     * @see Account#Account(Integer, String, String, String, Date, Integer, Integer)
     */
    public Account() {

    }

    /**
     * Constructor for creating Account object with associated user id
     *
     * @param userId - user id which associated with current user
     */
    public Account(Integer userId) {
        this.userId = userId;
    }

    /**
     * Constructor with arguments and without id for creating Account Object
     *
     * @param firstName - account's first name
     * @param lastName  - account's last name
     * @param email     - account's email
     * @param birthDate - account's birthdate
     * @param roleId    - account's role
     * @param userId    - user id which associated with current user
     */
    public Account(String firstName, String lastName, String email, Date birthDate, Integer roleId, Integer userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.role = Role.getById(roleId);
        this.userId = userId;
    }

    /**
     * Constructor with all amount of arguments for creating Account Object
     *
     * @param id        - account's id
     * @param firstName - account's first name
     * @param lastName  - account's last name
     * @param email     - account's email
     * @param birthDate - account's birthdate
     * @param roleId    - account's role
     * @param userId    - user id which associated with current user
     */
    public Account(Integer id, String firstName, String lastName, String email, Date birthDate, Integer roleId, Integer userId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.role = Role.getById(roleId);
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(firstName, account.firstName) && Objects.equals(lastName, account.lastName) && Objects.equals(email, account.email) && Objects.equals(birthDate, account.birthDate) && role == account.role && userId.equals(account.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, birthDate, role, userId);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", role=" + role +
                ", userId=" + userId +
                '}';
    }
}
