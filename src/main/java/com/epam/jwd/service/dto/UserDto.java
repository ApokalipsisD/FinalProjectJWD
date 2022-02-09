package com.epam.jwd.service.dto;

/**
 * UserDto class for User entity
 */
public class UserDto extends AbstractDto<Integer> {
    private String login;
    private String password;

    public UserDto(){

    }

    public UserDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserDto(Integer id, String login, String password) {
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

        UserDto userDto = (UserDto) o;

        if (!login.equals(userDto.login)) return false;
        return password.equals(userDto.password);
    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
