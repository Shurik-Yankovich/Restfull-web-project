package com.expexchangeservice.model.dto;

import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.model.entities.Theme;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class LessonDto {

    private int id;
    private Theme theme;
    private ProfileDto professor;
    private Type type;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public ProfileDto getProfessor() {
        return professor;
    }

    public void setProfessor(ProfileDto professor) {
        this.professor = professor;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
