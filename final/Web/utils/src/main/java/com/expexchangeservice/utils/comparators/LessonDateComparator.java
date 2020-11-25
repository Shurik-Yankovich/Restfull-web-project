package com.expexchangeservice.utils.comparators;

import com.expexchangeservice.model.entities.Lesson;

import java.util.Comparator;

public class LessonDateComparator implements Comparator<Lesson> {
    @Override
    public int compare(Lesson lesson, Lesson comparableLesson) {
        return lesson.getDate().compareTo(comparableLesson.getDate());
    }
}

