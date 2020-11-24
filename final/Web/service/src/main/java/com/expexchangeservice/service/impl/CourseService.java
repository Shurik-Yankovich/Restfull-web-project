package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.CourseDto;
import com.expexchangeservice.model.entities.*;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.repository.interfaces.ICourseRepository;
import com.expexchangeservice.service.interfaces.ICourseService;
import com.expexchangeservice.service.interfaces.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public void addCourse(CourseDto courseDto) {
        Course course = convertDtoToCourse(new Course(), courseDto);
        course.setPrice(100 * course.getCountLesson());
        courseRepository.create(course);
    }

    @Override
    public void changeCourse(int courseId, CourseDto courseDto) {
        Course course = courseRepository.read(courseId);
        course = convertDtoToCourse(course, courseDto);
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
        return convertCourseToDto(course);
    }

    @Override
    public List<CourseDto> getAll() {
        List<Course> courses = courseRepository.readAll();
        return convertCourseListToDtoList(courses);
    }

    @Override
    public List<CourseDto> getCoursesOnTheDate(LocalDate date) {
        List<Course> courses = courseRepository.findByDate(date);
        return convertCourseListToDtoList(courses);
    }

    @Override
    public List<CourseDto> getCoursesAfterDate(LocalDate date) {
        List<Course> courses = courseRepository.findAfterDate(date);
        return convertCourseListToDtoList(courses);
    }

    @Override
    public List<CourseDto> getCoursesOnTheSection(Section section) {
        List<Course> courses = courseRepository.findBySection(section);
        return convertCourseListToDtoList(courses);
    }

    @Override
    public List<CourseDto> getCoursesForTheProfessor(UserProfile professor) {
        List<Course> courses = courseRepository.findByProfessor(professor);
        return convertCourseListToDtoList(courses);
    }

    @Override
    public List<CourseDto> getCoursesByType(Type lessonType) {
        List<Course> courses = courseRepository.findByType(lessonType);
        return convertCourseListToDtoList(courses);
    }

    @Override
    public void addReview(Integer courseId, Review review) {
        reviewService.addReview(review);
        Course course = courseRepository.read(courseId);
        if (course.getReviews() == null) {
            course.setReviews(new HashSet<Review>());
        }
        course.getReviews().add(review);
        courseRepository.update(course);
    }

    @Override
    public Set<Review> getReviewOnTheLesson(Integer courseId) {
        Course course = courseRepository.read(courseId);
        return course.getReviews();
    }

    private Course convertDtoToCourse(Course course, CourseDto courseDto) {
        course.setId(courseDto.getId());
        course.setSection(courseDto.getSection());
        course.setProfessor(courseDto.getProfessor());
        course.setType(courseDto.getType());
        course.setDateStart(courseDto.getDateStart());
        course.setCountLesson(courseDto.getCountLesson());
        return course;
    }

    private CourseDto convertCourseToDto(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setSection(course.getSection());
        courseDto.setProfessor(course.getProfessor());
        courseDto.setType(course.getType());
        courseDto.setDateStart(course.getDateStart());
        courseDto.setCountLesson(course.getCountLesson());
        return courseDto;
    }

    private List<CourseDto> convertCourseListToDtoList(List<Course> courses) {
        List<CourseDto> courseDtoList = new ArrayList<>();
        for (Course course : courses) {
            courseDtoList.add(convertCourseToDto(course));
        }
        return courseDtoList;
    }
}
