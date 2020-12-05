package com.expexchangeservice.model.dto;

import java.util.Objects;

public class UserCreds {

    private UserDto user;
    private String newPassword;
    private String passwordConfirm;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCreds userCreds = (UserCreds) o;
        return Objects.equals(user, userCreds.user) &&
                Objects.equals(newPassword, userCreds.newPassword) &&
                Objects.equals(passwordConfirm, userCreds.passwordConfirm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, newPassword, passwordConfirm);
    }
}
