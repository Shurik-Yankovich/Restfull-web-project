package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.dto.CourseDto;
import com.expexchangeservice.model.entities.*;
import com.expexchangeservice.model.enums.Type;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ICourseService {

    void createCourse(CourseDto courseDto);
    boolean updateCourse(int courseId, CourseDto courseDto);
    boolean deleteCourse(Integer courseId);
    CourseDto getCourseById(Integer courseId);
    List<CourseDto> getAll();
    List<CourseDto> getCoursesOnTheDate(LocalDate date);
    List<CourseDto> getCoursesAfterDate(LocalDate date);
    List<CourseDto> getCoursesOnTheSection(Section section);
    List<CourseDto> getCoursesForTheProfessor(UserProfile professor);
    List<CourseDto> getCoursesByType(Type courseType);
    boolean addReview(Integer courseId, Review review);
    Set<Review> getReviewOnTheLesson(Integer courseId);
}
