package com.expexchangeservice.model.dto;

import com.expexchangeservice.model.enums.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class CourseDto {

    private long id;
    private SectionDto section;
    private ProfileDto professor;
    private Type type;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate dateStart;
    private int countLesson;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SectionDto getSection() {
        return section;
    }

    public void setSection(SectionDto section) {
        this.section = section;
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
