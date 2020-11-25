package com.expexchangeservice.utils.comparators;

import com.expexchangeservice.model.entities.Course;

import java.util.Comparator;

public class CourseStartDateComparator implements Comparator<Course> {
    @Override
    public int compare(Course course, Course comparableCourse) {
        return course.getDateStart().compareTo(comparableCourse.getDateStart());
    }
}
