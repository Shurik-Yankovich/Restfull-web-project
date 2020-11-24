package com.expexchangeservice.model.dto;

import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.model.enums.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class CourseDto {

    private int id;
    private Section section;
    //TODO: заменить UserProfile на логин и имя пользователя
    private UserProfile professor;
    private Type type;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate dateStart;
    private int countLesson;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public UserProfile getProfessor() {
        return professor;
    }

    public void setProfessor(UserProfile professor) {
        this.professor = professor;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public int getCountLesson() {
        return countLesson;
    }

    public void setCountLesson(int countLesson) {
        this.countLesson = countLesson;
    }
}
