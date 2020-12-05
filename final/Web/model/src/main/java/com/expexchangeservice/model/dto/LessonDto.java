package com.expexchangeservice.model.dto;

import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.model.entities.Theme;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Objects;

public class LessonDto {

    private long id;
    private Theme theme;
    private ProfileDto professor;
    private Type type;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonDto lessonDto = (LessonDto) o;
        return id == lessonDto.id &&
                Objects.equals(theme, lessonDto.theme) &&
                Objects.equals(professor, lessonDto.professor) &&
                type == lessonDto.type &&
                Objects.equals(date, lessonDto.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, theme, professor, type, date);
    }
}
