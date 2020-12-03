package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.CourseDto;
import com.expexchangeservice.model.dto.ProfileDto;
import com.expexchangeservice.model.entities.*;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.repository.interfaces.ICourseRepository;
import com.expexchangeservice.service.converter.DtoConverter;
import com.expexchangeservice.service.interfaces.ICourseService;
import com.expexchangeservice.service.interfaces.IReviewService;
import com.expexchangeservice.service.interfaces.IUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CourseService implements ICourseService {

    private ICourseRepository courseRepository;
    private IReviewService reviewService;
    private IUserProfileService profileService;
    private DtoConverter converter;

    @Value("${reward_for_lesson:100}")
    private static int REWARD_FOR_LESSON;

    @Autowired
    public CourseService(ICourseRepository courseRepository, IReviewService reviewService,
                         IUserProfileService profileService, DtoConverter converter) {
        this.courseRepository = courseRepository;
        this.reviewService = reviewService;
        this.profileService = profileService;
        this.converter = converter;
    }

    @Override
    public void createCourse(CourseDto courseDto) {
        Course course = converter.convertDtoToCourse(new Course(), courseDto);
        course.setReward(REWARD_FOR_LESSON * course.getCountLesson());
        courseRepository.create(course);
    }

    @Override
    public boolean updateCourse(int courseId, CourseDto courseDto) {
        Course course = converter.convertDtoToCourse(courseRepository.read(courseId), courseDto);
        if (course == null) {
            return false;
        }
        courseRepository.update(course);
        return true;
    }

    @Override
    public boolean deleteCourse(Integer courseId) {
        Course course = courseRepository.read(courseId);
        if (course == null) {
            return false;
        }
        courseRepository.delete(course);
        return true;
    }

    @Override
    public CourseDto getCourseById(Integer courseId) {
        Course course = courseRepository.read(courseId);
        return converter.convertCourseToDto(course);
    }

    @Override
    public List<CourseDto> getAll() {
        List<Course> courses = courseRepository.readAll();
        return converter.convertCourseListToDtoList(courses);
    }

    @Override
    public List<CourseDto> getCoursesOnTheDate(LocalDate date) {
        List<Course> courses = courseRepository.findByDate(date);
        return converter.convertCourseListToDtoList(courses);
    }

    @Override
    public List<CourseDto> getCoursesAfterDate(LocalDate date) {
        List<Course> courses = courseRepository.findAfterDate(date);
        return converter.convertCourseListToDtoList(courses);
    }

    @Override
    public List<CourseDto> getCoursesOnTheSection(Section section) {
        List<Course> courses = courseRepository.findBySection(section);
        return converter.convertCourseListToDtoList(courses);
    }

    @Override
    public List<CourseDto> getCoursesForTheProfessor(ProfileDto profileDto) {
        UserProfile professor = profileService.findProfileByUsername(profileDto.getUsername());
        List<Course> courses = courseRepository.findByProfessor(professor);
        return converter.convertCourseListToDtoList(courses);
    }

    @Override
    public List<CourseDto> getCoursesByType(Type lessonType) {
        List<Course> courses = courseRepository.findByType(lessonType);
        return converter.convertCourseListToDtoList(courses);
    }

    @Override
    public boolean addReview(Integer courseId, Review review) {
        reviewService.addReview(review);
        Course course = courseRepository.read(courseId);
        if (course == null) {
            return false;
        }
        if (course.getReviews() == null) {
            course.setReviews(new HashSet<>());
        }
        course.getReviews().add(review);
        courseRepository.update(course);
        return true;
    }

    @Override
    public Set<Review> getReviewOnTheLesson(Integer courseId) {
        Course course = courseRepository.read(courseId);
        return course.getReviews();
    }

    @Override
    public int getRewardForCoursesByProfessor(String username) {
        UserProfile professor = profileService.findProfileByUsername(username);
        List<Course> courses = courseRepository.findByProfessor(professor);
        int sum = 0;
        for (Course course : courses) {
            sum += course.getReward();
        }
        return sum;
    }

    @Override
    public boolean changeRewardByCourseId(int lessonId, int reward) {
        Course course = courseRepository.read(lessonId);
        if (course == null) {
            return false;
        }
        course.setReward(reward);
        courseRepository.update(course);
        return true;
    }

}
