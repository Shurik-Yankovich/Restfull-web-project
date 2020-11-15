package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.entities.Course;
import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.repository.interfaces.ICourseRepository;
import com.expexchangeservice.service.interfaces.ICourseService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class CourseService implements ICourseService {

    private ICourseRepository courseRepository;
    private SessionFactory sessionFactory;

    @Autowired
    public CourseService(ICourseRepository courseRepository, SessionFactory sessionFactory) {
        this.courseRepository = courseRepository;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addCourse(Course course) {

    }

    @Override
    public void editCourse(Course course) {

    }

    @Override
    public void deleteCourse(Integer primaryKey) {

    }

    @Override
    public Set<Course> getAll() {
        return null;
    }

    @Override
    public Set<Course> getCoursesOnTheDate(LocalDate date) {
        return null;
    }

    @Override
    public Set<Course> getCoursesAfterDate(LocalDate date) {
        return null;
    }

    @Override
    public Set<Course> getCoursesForMember(UserProfile member) {
        return null;
    }

    @Override
    public Set<Course> getCoursesOnTheTheme(Section section) {
        return null;
    }

    @Override
    public Set<Course> getCoursesForTheProfessor(UserProfile professor) {
        return null;
    }

    @Override
    public Set<Course> getCoursesByType(Type lessonType) {
        return null;
    }

    @Override
    public void addMemberToTheCourse(Lesson lesson, UserProfile userProfile) {

    }
}
