package com.epam.jwd.dao.entity;

/**
 * User entity class which extends Entity with Integer id field
 */
public class User extends Entity<Integer> {
    /**
     * String field with user's login
     */
    private String login;
    /**
     * String field with user's password
     */
    private String password;

    /**
     * Constructor without arguments for creating empty User Object
     *
     * @see User#User(String, String)
     * @see User#User(Integer, String, String)
     */
    public User() {

    }

    /**
     * Constructor with arguments and without id for creating User Object
     *
     * @param login    - user's login
     * @param password - user's password
     */
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * Constructor with all amount of arguments for creating User object
     *
     * @param id       - user's id
     * @param login    - user's login
     * @param password - user's password
     */
    public User(Integer id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!login.equals(user.login)) return false;
        return password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
