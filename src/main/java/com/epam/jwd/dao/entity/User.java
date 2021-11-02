package com.epam.jwd.dao.entity;

public class User extends Entity<Integer> {
    private String login;
    private String password;
    private Account account;

    public User(String login, String password, Account account) {
        this.login = login;
        this.password = password;
        this.account = account;
    }

    public User(Integer id, String login, String password, Account account) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.account = account;
    }

    public User() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!login.equals(user.login)) return false;
        if (!password.equals(user.password)) return false;
        return account.equals(user.account);
    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + account.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", account=" + account +
                '}';
    }
}
