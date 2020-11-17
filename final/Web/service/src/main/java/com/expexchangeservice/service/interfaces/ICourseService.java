package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.entities.Course;
import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.model.enums.Type;

import java.time.LocalDate;
import java.util.List;

public interface ICourseService {

    void addCourse(Course course);
    void editCourse(Course course);
    void deleteCourse(Integer primaryKey);
    List<Course> getAll();
    List<Course> getCoursesOnTheDate(LocalDate date);
    List<Course> getCoursesAfterDate(LocalDate date);
    List<Course> getCoursesForMember(UserProfile member);
    List<Course> getCoursesOnTheTheme(Section section);
    List<Course> getCoursesForTheProfessor(UserProfile professor);
    List<Course> getCoursesByType(Type lessonType);
    void addMemberToTheCourse(Lesson lesson, UserProfile userProfile);
}
