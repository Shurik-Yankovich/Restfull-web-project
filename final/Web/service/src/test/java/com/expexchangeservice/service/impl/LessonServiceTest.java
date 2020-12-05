package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.LessonDto;
import com.expexchangeservice.model.dto.ProfileDto;
import com.expexchangeservice.model.dto.ThemeDto;
import com.expexchangeservice.model.entities.*;
import com.expexchangeservice.model.enums.Role;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.repository.interfaces.ILessonRepository;
import com.expexchangeservice.service.config.LessonServiceTestContextConfiguration;
import com.expexchangeservice.service.interfaces.ILessonService;
import com.expexchangeservice.service.interfaces.IReviewService;
import com.expexchangeservice.service.interfaces.IUserProfileService;
import com.expexchangeservice.service.interfaces.IUserService;
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
@ContextConfiguration(classes = LessonServiceTestContextConfiguration.class)
public class LessonServiceTest {

    @Autowired
    private ILessonService lessonService;
    @Autowired
    private ILessonRepository lessonRepository;
    @Autowired
    private IReviewService reviewService;
    @Autowired
    private IUserProfileService profileService;
    @Autowired
    private IUserService userService;

    private static Lesson EXPECTED_LESSON;
    private static LessonDto EXPECTED_LESSON_DTO;
    private static List<LessonDto> EXPECTED_LESSON_DTO_LIST;
    private static Review EXPECTED_REVIEW;
    private static Set<Review> EXPECTED_REVIEW_SET;
    private static User EXPECTED_USER;
    private static UserProfile EXPECTED_USER_PROFILE;
    private static final long ID = 1L;
    private static final int EXPECTED_REWARD = 100;

    @BeforeAll
    public static void init() {
        Theme theme = new Theme("Test theme");
        theme.setId(ID);
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
        EXPECTED_LESSON = new Lesson();
        EXPECTED_LESSON.setId(ID);
        EXPECTED_LESSON.setTheme(theme);
        EXPECTED_LESSON.setProfessor(EXPECTED_USER_PROFILE);
        EXPECTED_LESSON.setType(Type.SINGLE);
        EXPECTED_LESSON.setDate(LocalDate.now());
        EXPECTED_LESSON.setReward(EXPECTED_REWARD);
        EXPECTED_LESSON.setReviews(EXPECTED_REVIEW_SET);
        EXPECTED_LESSON.setMembers(new HashSet<>(Arrays.asList(EXPECTED_USER_PROFILE)));
        EXPECTED_LESSON_DTO = new LessonDto();
        EXPECTED_LESSON_DTO.setId(EXPECTED_LESSON.getId());
        EXPECTED_LESSON_DTO.setTheme(EXPECTED_LESSON.getTheme());
        EXPECTED_LESSON_DTO.setProfessor(profileDto);
        EXPECTED_LESSON_DTO.setType(EXPECTED_LESSON.getType());
        EXPECTED_LESSON_DTO.setDate(EXPECTED_LESSON.getDate());
        EXPECTED_LESSON_DTO_LIST = Arrays.asList(EXPECTED_LESSON_DTO);
    }

    @Test
    public void updateLessonWithoutErrors() {
        Lesson actualLesson = new Lesson();
        actualLesson.setMembers(EXPECTED_LESSON.getMembers());
        actualLesson.setReviews(EXPECTED_LESSON.getReviews());
        actualLesson.setReward(EXPECTED_REWARD);

        doReturn(actualLesson).when(lessonRepository).read(anyLong());
        doReturn(EXPECTED_USER).when(userService).loadUserByUsername(anyString());
        doNothing().when(lessonRepository).update(any(Lesson.class));

        assertTrue(lessonService.updateLesson(ID, EXPECTED_LESSON_DTO));
        assertEquals(EXPECTED_LESSON, actualLesson);
    }

    @Test
    public void updateLessonWhenLessonDtoIsNull() {
        doReturn(EXPECTED_LESSON).when(lessonRepository).read(anyLong());

        assertFalse(lessonService.updateLesson(ID, null));
    }

    @Test
    public void deleteLessonWithoutErrors() {
        doReturn(EXPECTED_LESSON).when(lessonRepository).read(anyLong());
        doNothing().when(lessonRepository).delete(any(Lesson.class));

        assertTrue(lessonService.deleteLesson(ID));
    }

    @Test
    public void deleteLessonWhenLessonNotInDatabase() {
        doReturn(null).when(lessonRepository).read(anyLong());
        doNothing().when(lessonRepository).delete(any(Lesson.class));

        assertFalse(lessonService.deleteLesson(ID));
    }

    @Test
    public void getLessonByIdWithoutErrors() {
        doReturn(EXPECTED_LESSON).when(lessonRepository).read(anyLong());

        LessonDto actualLessonDto = lessonService.getLessonById(ID);
        assertEquals(EXPECTED_LESSON_DTO, actualLessonDto);
    }

    @Test
    public void getAllWithoutErrors() {
        doReturn(Arrays.asList(EXPECTED_LESSON)).when(lessonRepository).readAll();

        List<LessonDto> actualLessonDtoList = lessonService.getAll();
        assertEquals(EXPECTED_LESSON_DTO_LIST, actualLessonDtoList);
    }

    @Test
    public void addReviewWithoutErrors() {
        Lesson actualLesson = getLesson();
        actualLesson.setReward(EXPECTED_REWARD);

        doNothing().when(reviewService).createReview(any(Review.class));
        doReturn(actualLesson).when(lessonRepository).read(anyLong());
        doNothing().when(lessonRepository).update(any(Lesson.class));

        assertTrue(lessonService.addReview(ID, EXPECTED_REVIEW));
        assertEquals(EXPECTED_LESSON, actualLesson);
    }

    @Test
    public void addReviewWhenLessonNotInDatabase() {
        doReturn(null).when(lessonRepository).read(anyLong());

        assertFalse(lessonService.addReview(ID, EXPECTED_REVIEW));
    }

    @Test
    public void getReviewOnTheLessonWithoutErrors() {
        doReturn(EXPECTED_LESSON).when(lessonRepository).read(anyLong());

        Set<Review> actualReviewSet = lessonService.getReviewOnTheLesson(ID);
        assertEquals(EXPECTED_REVIEW_SET, actualReviewSet);
    }

    @Test
    public void getReviewOnTheLessonWhenLessonNotInDatabase() {
        doReturn(null).when(lessonRepository).read(anyLong());

        Set<Review> actualReviewSet = lessonService.getReviewOnTheLesson(ID);
        assertNull(actualReviewSet);
    }

    @Test
    public void getRewardForLessonsByProfessorWithoutErrors() {
        doReturn(EXPECTED_USER_PROFILE).when(profileService).findProfileByUsername(anyString());
        doReturn(Arrays.asList(EXPECTED_LESSON)).when(lessonRepository).findByProfessor(EXPECTED_USER_PROFILE);

        int actualReward = lessonService.getRewardForLessonsByProfessor("user");
        assertEquals(EXPECTED_REWARD, actualReward);
    }

    @Test
    public void changeRewardByLessonIdWithoutErrors() {
        Lesson actualLesson = getLesson();
        actualLesson.setReviews(EXPECTED_LESSON.getReviews());
        actualLesson.setReward(200);

        doReturn(actualLesson).when(lessonRepository).read(anyLong());
        doReturn(EXPECTED_USER).when(userService).loadUserByUsername(anyString());
        doNothing().when(lessonRepository).update(any(Lesson.class));

        assertTrue(lessonService.changeRewardByLessonId(ID, EXPECTED_REWARD));
        assertEquals(EXPECTED_LESSON, actualLesson);
    }

    @Test
    public void changeRewardByLessonIdWhenLessonNotInDatabase() {
        doReturn(null).when(lessonRepository).read(anyLong());

        assertFalse(lessonService.changeRewardByLessonId(ID, EXPECTED_REWARD));
    }

    private Lesson getLesson() {
        Lesson lesson = new Lesson();
        lesson.setId(EXPECTED_LESSON.getId());
        lesson.setTheme(EXPECTED_LESSON.getTheme());
        lesson.setProfessor(EXPECTED_LESSON.getProfessor());
        lesson.setType(EXPECTED_LESSON.getType());
        lesson.setDate(EXPECTED_LESSON.getDate());
        lesson.setMembers(EXPECTED_LESSON.getMembers());
        return lesson;
    }
}
