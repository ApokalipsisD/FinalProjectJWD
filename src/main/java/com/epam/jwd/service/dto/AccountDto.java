package com.epam.jwd.service.dto;

import com.epam.jwd.dao.entity.Role;

import java.sql.Date;
import java.util.Objects;

/**
 * AccountDto class for Account entity
 */
public class AccountDto extends AbstractDto<Integer>{
    private String firstName;
    private String lastName;
    private String email;
    private Date birthDate;
    private Role role;
    private Integer userId;

    public AccountDto(){

    }

    public AccountDto(String firstName, String lastName, String email, Date birthDate, Integer roleId, Integer userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.role = Role.getById(roleId);
        this.userId = userId;
    }

    public AccountDto(Integer id, String firstName, String lastName, String email, Date birthDate, Integer roleId, Integer userId) {
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
        AccountDto that = (AccountDto) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(birthDate, that.birthDate) && role == that.role && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, birthDate, role, userId);
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", role=" + role +
                ", userId=" + userId +
                '}';
    }
}
