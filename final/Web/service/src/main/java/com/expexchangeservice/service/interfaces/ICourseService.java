package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.dto.CourseDto;
import com.expexchangeservice.model.entities.*;
import com.expexchangeservice.model.enums.Type;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ICourseService {

    void addCourse(CourseDto courseDto);
    void changeCourse(int courseId, CourseDto courseDto);
    void deleteCourse(Integer courseId);
    CourseDto getCourseById(Integer courseId);
    List<CourseDto> getAll();
    List<CourseDto> getCoursesOnTheDate(LocalDate date);
    List<CourseDto> getCoursesAfterDate(LocalDate date);
    List<CourseDto> getCoursesOnTheSection(Section section);
    List<CourseDto> getCoursesForTheProfessor(UserProfile professor);
    List<CourseDto> getCoursesByType(Type courseType);
    void addReview(Integer courseId, Review review);
    Set<Review> getReviewOnTheLesson(Integer courseId);
}
