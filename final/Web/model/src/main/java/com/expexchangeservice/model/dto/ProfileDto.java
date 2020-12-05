package com.expexchangeservice.model.dto;

import java.util.Objects;

public class ProfileDto {

    private long id;
    private String username;
    private String fullName;
    private String placeOfWork;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPlaceOfWork() {
        return placeOfWork;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileDto that = (ProfileDto) o;
        return id == that.id &&
                Objects.equals(username, that.username) &&
                Objects.equals(fullName, that.fullName) &&
                Objects.equals(placeOfWork, that.placeOfWork);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, fullName, placeOfWork);
    }
}
