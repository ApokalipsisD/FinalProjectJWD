package com.epam.jwd.dao.entity;

import java.util.Date;

public class Account extends Entity<Integer> {
    private String firstName;
    private String secondName;
    private String email;
    private Date birthDate;
    private Role role;

    public Account(Integer id, String firstName, String secondName, String email, Date birthDate, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.birthDate = birthDate;
        this.role = role;
    }

    public Account(String firstName, String secondName, String email, Date birthDate, Role role) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.birthDate = birthDate;
        this.role = role;
    }

    public Account() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (!firstName.equals(account.firstName)) return false;
        if (!secondName.equals(account.secondName)) return false;
        if (!email.equals(account.email)) return false;
        if (!birthDate.equals(account.birthDate)) return false;
        return role == account.role;
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + secondName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + birthDate.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", role=" + role +
                '}';
    }
}
