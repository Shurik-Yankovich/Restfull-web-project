package com.expexchangeservice.utils;

import com.expexchangeservice.model.dto.LessonDto;
import com.expexchangeservice.model.entities.Lesson;

public class DtoConverter {

    public static Lesson convertDtoToLesson(LessonDto lessonDto) {
        Lesson lesson = new Lesson();
        lesson.setId(lessonDto.getId());
        lesson.setTheme(lessonDto.getTheme());
        lesson.setProfessor(lessonDto.getProfessor());
        lesson.setType(lessonDto.getType());
        lesson.setDate(lessonDto.getDate());
        return lesson;
    }
}
