package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.CourseDto;
import com.expexchangeservice.model.entities.*;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.repository.interfaces.ICourseRepository;
import com.expexchangeservice.service.converter.DtoConverter;
import com.expexchangeservice.service.interfaces.ICourseService;
import com.expexchangeservice.service.interfaces.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CourseService implements ICourseService {

    private ICourseRepository courseRepository;
    private IReviewService reviewService;

    @Autowired
    public CourseService(ICourseRepository courseRepository, IReviewService reviewService) {
        this.courseRepository = courseRepository;
        this.reviewService = reviewService;
    }

    @Override
    public void createCourse(CourseDto courseDto) {
        Course course = DtoConverter.convertDtoToCourse(new Course(), courseDto);
        course.setPrice(100 * course.getCountLesson());
        courseRepository.create(course);
    }

    @Override
    public void updateCourse(int courseId, CourseDto courseDto) {
        Course course = DtoConverter.convertDtoToCourse(courseRepository.read(courseId), courseDto);
        courseRepository.update(course);
    }

    @Override
    public void deleteCourse(Integer courseId) {
        Course course = courseRepository.read(courseId);
        courseRepository.delete(course);
    }

    @Override
    public CourseDto getCourseById(Integer courseId) {
        Course course = courseRepository.read(courseId);
        return DtoConverter.convertCourseToDto(course);
    }

    @Override
    public List<CourseDto> getAll() {
        List<Course> courses = courseRepository.readAll();
        return DtoConverter.convertCourseListToDtoList(courses);
    }

    @Override
    public List<CourseDto> getCoursesOnTheDate(LocalDate date) {
        List<Course> courses = courseRepository.findByDate(date);
        return DtoConverter.convertCourseListToDtoList(courses);
    }

    @Override
    public List<CourseDto> getCoursesAfterDate(LocalDate date) {
        List<Course> courses = courseRepository.findAfterDate(date);
        return DtoConverter.convertCourseListToDtoList(courses);
    }

    @Override
    public List<CourseDto> getCoursesOnTheSection(Section section) {
        List<Course> courses = courseRepository.findBySection(section);
        return DtoConverter.convertCourseListToDtoList(courses);
    }

    @Override
    public List<CourseDto> getCoursesForTheProfessor(UserProfile professor) {
        List<Course> courses = courseRepository.findByProfessor(professor);
        return DtoConverter.convertCourseListToDtoList(courses);
    }

    @Override
    public List<CourseDto> getCoursesByType(Type lessonType) {
        List<Course> courses = courseRepository.findByType(lessonType);
        return DtoConverter.convertCourseListToDtoList(courses);
    }

    @Override
    public void addReview(Integer courseId, Review review) {
        reviewService.addReview(review);
        Course course = courseRepository.read(courseId);
        if (course.getReviews() == null) {
            course.setReviews(new HashSet<>());
        }
        course.getReviews().add(review);
        courseRepository.update(course);
    }

    @Override
    public Set<Review> getReviewOnTheLesson(Integer courseId) {
        Course course = courseRepository.read(courseId);
        return course.getReviews();
    }

}
