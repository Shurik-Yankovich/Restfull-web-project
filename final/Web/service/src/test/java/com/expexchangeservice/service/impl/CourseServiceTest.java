package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.*;
import com.expexchangeservice.model.entities.*;
import com.expexchangeservice.model.enums.Role;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.repository.interfaces.ICourseRepository;
import com.expexchangeservice.service.config.CourseServiceTestContextConfiguration;
import com.expexchangeservice.service.interfaces.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CourseServiceTestContextConfiguration.class)
public class CourseServiceTest {

    @Autowired
    private ICourseService courseService;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private IReviewService reviewService;
    @Autowired
    private IUserProfileService profileService;
    @Autowired
    private IUserService userService;

    private static Course EXPECTED_COURSE;
    private static CourseDto EXPECTED_COURSE_DTO;
    private static List<CourseDto> EXPECTED_COURSE_DTO_LIST;
    private static Review EXPECTED_REVIEW;
    private static Set<Review> EXPECTED_REVIEW_SET;
    private static User EXPECTED_USER;
    private static UserProfile EXPECTED_USER_PROFILE;
    private static final long ID = 1L;
    private static final int EXPECTED_REWARD = 100;

    @BeforeAll
    public static void init() {
        int countLesson = 5;
        Section section = new Section("Test section");
        section.setId(ID);
        SectionDto sectionDto = new SectionDto();
        sectionDto.setId(section.getId());
        sectionDto.setTitle(section.getTitle());
        EXPECTED_USER = new User();
        EXPECTED_USER.setId(ID);
        EXPECTED_USER.setUsername("user");
        EXPECTED_USER.setPassword("123");
        EXPECTED_USER.setRole(Role.ROLE_USER);
        EXPECTED_USER_PROFILE = new UserProfile();
        EXPECTED_USER_PROFILE.setId(ID);
        EXPECTED_USER_PROFILE.setFullName("Name");
        EXPECTED_USER_PROFILE.setPlaceOfWork("Place of work");
        EXPECTED_USER_PROFILE.setUser(EXPECTED_USER);
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(EXPECTED_USER_PROFILE.getId());
        profileDto.setUsername(EXPECTED_USER_PROFILE.getUser().getUsername());
        profileDto.setFullName(EXPECTED_USER_PROFILE.getFullName());
        profileDto.setPlaceOfWork(EXPECTED_USER_PROFILE.getPlaceOfWork());
        EXPECTED_REVIEW = new Review();
        EXPECTED_REVIEW.setId(ID);
        EXPECTED_REVIEW.setUsername(EXPECTED_USER_PROFILE.getUser().getUsername());
        EXPECTED_REVIEW.setReview("Review");
        EXPECTED_REVIEW_SET = new HashSet<>(Arrays.asList(EXPECTED_REVIEW));
        EXPECTED_COURSE = new Course();
        EXPECTED_COURSE.setId(ID);
        EXPECTED_COURSE.setSection(section);
        EXPECTED_COURSE.setProfessor(EXPECTED_USER_PROFILE);
        EXPECTED_COURSE.setType(Type.SINGLE);
        EXPECTED_COURSE.setDateStart(LocalDate.now());
        EXPECTED_COURSE.setReward(EXPECTED_REWARD);
        EXPECTED_COURSE.setCountLesson(countLesson);
        EXPECTED_COURSE.setReviews(EXPECTED_REVIEW_SET);
        EXPECTED_COURSE.setMembers(new HashSet<>(Arrays.asList(EXPECTED_USER_PROFILE)));
        EXPECTED_COURSE_DTO = new CourseDto();
        EXPECTED_COURSE_DTO.setId(EXPECTED_COURSE.getId());
        EXPECTED_COURSE_DTO.setSection(sectionDto);
        EXPECTED_COURSE_DTO.setProfessor(profileDto);
        EXPECTED_COURSE_DTO.setType(EXPECTED_COURSE.getType());
        EXPECTED_COURSE_DTO.setDateStart(EXPECTED_COURSE.getDateStart());
        EXPECTED_COURSE_DTO.setCountLesson(EXPECTED_COURSE.getCountLesson());
        EXPECTED_COURSE_DTO_LIST = Arrays.asList(EXPECTED_COURSE_DTO);
    }

    @Test
    public void updateCourseWithoutErrors() {
        Course actualCourse = new Course();
        actualCourse.setReward(EXPECTED_REWARD);
        actualCourse.setMembers(EXPECTED_COURSE.getMembers());
        actualCourse.setReviews(EXPECTED_COURSE.getReviews());

        doReturn(actualCourse).when(courseRepository).read(anyLong());
        doReturn(EXPECTED_USER).when(userService).loadUserByUsername(anyString());
        doNothing().when(courseRepository).update(any(Course.class));

        assertTrue(courseService.updateCourse(ID, EXPECTED_COURSE_DTO));
        assertEquals(EXPECTED_COURSE, actualCourse);
    }

    @Test
    public void updateCourseWhenCourseDtoIsNull() {
        doReturn(EXPECTED_COURSE).when(courseRepository).read(anyLong());

        assertFalse(courseService.updateCourse(ID, null));
    }

    @Test
    public void deleteCourseWithoutErrors() {
        doReturn(EXPECTED_COURSE).when(courseRepository).read(anyLong());
        doNothing().when(courseRepository).delete(any(Course.class));

        assertTrue(courseService.deleteCourse(ID));
    }

    @Test
    public void deleteCourseWhenCourseNotInDatabase() {
        doReturn(null).when(courseRepository).read(anyLong());
        doNothing().when(courseRepository).delete(any(Course.class));

        assertFalse(courseService.deleteCourse(ID));
    }

    @Test
    public void getCourseByIdWithoutErrors() {
        doReturn(EXPECTED_COURSE).when(courseRepository).read(anyLong());

        CourseDto actualCourseDto = courseService.getCourseById(ID);
        assertEquals(EXPECTED_COURSE_DTO, actualCourseDto);
    }

    @Test
    public void getAllWithoutErrors() {
        doReturn(Arrays.asList(EXPECTED_COURSE)).when(courseRepository).readAll();

        List<CourseDto> actualCourseDtoList = courseService.getAll();
        assertEquals(EXPECTED_COURSE_DTO_LIST, actualCourseDtoList);
    }

    @Test
    public void addReviewWithoutErrors() {
        Course actualCourse = getCourse();
        actualCourse.setReward(EXPECTED_REWARD);

        doNothing().when(reviewService).createReview(any(Review.class));
        doReturn(actualCourse).when(courseRepository).read(anyLong());
        doNothing().when(courseRepository).update(any(Course.class));

        assertTrue(courseService.addReview(ID, EXPECTED_REVIEW));
        assertEquals(EXPECTED_COURSE, actualCourse);
    }

    @Test
    public void addReviewWhenCourseNotInDatabase() {
        doReturn(null).when(courseRepository).read(anyLong());

        assertFalse(courseService.addReview(ID, EXPECTED_REVIEW));
    }

    @Test
    public void getReviewOnTheCourseWithoutErrors() {
        doReturn(EXPECTED_COURSE).when(courseRepository).read(anyLong());

        Set<Review> actualReviewSet = courseService.getReviewOnTheCourse(ID);
        assertEquals(EXPECTED_REVIEW_SET, actualReviewSet);
    }

    @Test
    public void getReviewOnTheCourseWhenCourseNotInDatabase() {
        doReturn(null).when(courseRepository).read(anyLong());

        Set<Review> actualReviewSet = courseService.getReviewOnTheCourse(ID);
        assertNull(actualReviewSet);
    }

    @Test
    public void getRewardForCoursesByProfessorWithoutErrors() {
        doReturn(EXPECTED_USER_PROFILE).when(profileService).findProfileByUsername(anyString());
        doReturn(Arrays.asList(EXPECTED_COURSE)).when(courseRepository).findByProfessor(EXPECTED_USER_PROFILE);

        int actualReward = courseService.getRewardForCoursesByProfessor("user");
        assertEquals(EXPECTED_REWARD, actualReward);
    }

    @Test
    public void changeRewardByCourseIdWithoutErrors() {
        Course actualCourse = getCourse();
        actualCourse.setReviews(EXPECTED_COURSE.getReviews());
        actualCourse.setReward(200);

        doReturn(actualCourse).when(courseRepository).read(anyLong());
        doReturn(EXPECTED_USER).when(userService).loadUserByUsername(anyString());
        doNothing().when(courseRepository).update(any(Course.class));

        assertTrue(courseService.changeRewardByCourseId(ID, EXPECTED_REWARD));
        assertEquals(EXPECTED_COURSE, actualCourse);
    }

    @Test
    public void changeRewardByCourseIdWhenCourseNotInDatabase() {
        doReturn(null).when(courseRepository).read(anyLong());

        assertFalse(courseService.changeRewardByCourseId(ID, EXPECTED_REWARD));
    }

    private Course getCourse() {
        Course course = new Course();
        course.setId(EXPECTED_COURSE.getId());
        course.setSection(EXPECTED_COURSE.getSection());
        course.setProfessor(EXPECTED_COURSE.getProfessor());
        course.setType(EXPECTED_COURSE.getType());
        course.setDateStart(EXPECTED_COURSE.getDateStart());
        course.setCountLesson(EXPECTED_COURSE.getCountLesson());
        course.setMembers(EXPECTED_COURSE.getMembers());
        return course;
    }
}
