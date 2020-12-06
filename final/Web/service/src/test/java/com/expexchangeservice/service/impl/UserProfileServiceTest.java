package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.CourseDto;
import com.expexchangeservice.model.dto.LessonDto;
import com.expexchangeservice.model.dto.ProfileDto;
import com.expexchangeservice.model.dto.SectionDto;
import com.expexchangeservice.model.entities.*;
import com.expexchangeservice.model.enums.Role;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.repository.interfaces.IUserProfileRepository;
import com.expexchangeservice.service.config.UserProfileServiceTestContextConfiguration;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserProfileServiceTestContextConfiguration.class)
public class UserProfileServiceTest {

    @Autowired
    private IUserProfileService userProfileService;
    @Autowired
    private IUserProfileRepository profileRepository;
    @Autowired
    private IUserService userService;

    private static List<CourseDto> EXPECTED_COURSE_DTO_LIST;
    private static List<LessonDto> EXPECTED_LESSON_DTO_LIST;
    private static User EXPECTED_USER;
    private static UserProfile EXPECTED_USER_PROFILE;
    private static ProfileDto EXPECTED_PROFILE_DTO;
    private static final long ID = 1L;
    private static final String USERNAME = "user";

    @BeforeAll
    public static void init() {
        int countLesson = 5;
        int reward = 100;
        Section section = new Section("Test section");
        section.setId(ID);
        SectionDto sectionDto = new SectionDto();
        sectionDto.setId(section.getId());
        sectionDto.setTitle(section.getTitle());
        EXPECTED_USER = new User();
        EXPECTED_USER.setId(ID);
        EXPECTED_USER.setUsername(USERNAME);
        EXPECTED_USER.setPassword("123");
        EXPECTED_USER.setRole(Role.ROLE_ADMIN);
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
        Course course = new Course();
        course.setId(ID);
        course.setSection(section);
        course.setProfessor(EXPECTED_USER_PROFILE);
        course.setType(Type.SINGLE);
        course.setDateStart(LocalDate.now());
        course.setReward(reward);
        course.setCountLesson(countLesson);
        course.setMembers(new HashSet<>(Arrays.asList(EXPECTED_USER_PROFILE)));
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setSection(sectionDto);
        courseDto.setProfessor(profileDto);
        courseDto.setType(course.getType());
        courseDto.setDateStart(course.getDateStart());
        courseDto.setCountLesson(course.getCountLesson());
        EXPECTED_COURSE_DTO_LIST = Arrays.asList(courseDto);
        Theme theme = new Theme("Test theme");
        theme.setId(ID);
        Lesson lesson = new Lesson();
        lesson.setId(ID);
        lesson.setTheme(theme);
        lesson.setProfessor(EXPECTED_USER_PROFILE);
        lesson.setType(Type.SINGLE);
        lesson.setDate(LocalDate.now());
        lesson.setReward(reward);
        lesson.setMembers(new HashSet<>(Arrays.asList(EXPECTED_USER_PROFILE)));
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(lesson.getId());
        lessonDto.setTheme(lesson.getTheme());
        lessonDto.setProfessor(profileDto);
        lessonDto.setType(lesson.getType());
        lessonDto.setDate(lesson.getDate());
        EXPECTED_LESSON_DTO_LIST = Arrays.asList(lessonDto);
        EXPECTED_USER_PROFILE.setLessons(Arrays.asList(lesson));
        EXPECTED_USER_PROFILE.setCourses(Arrays.asList(course));
        EXPECTED_PROFILE_DTO = new ProfileDto();
        EXPECTED_PROFILE_DTO.setId(EXPECTED_USER_PROFILE.getId());
        EXPECTED_PROFILE_DTO.setUsername(EXPECTED_USER_PROFILE.getUser().getUsername());
        EXPECTED_PROFILE_DTO.setFullName(EXPECTED_USER_PROFILE.getFullName());
        EXPECTED_PROFILE_DTO.setPlaceOfWork(EXPECTED_USER_PROFILE.getPlaceOfWork());
    }

    @Test
    public void createUserProfileWithoutErrors() {
        doNothing().when(profileRepository).create(any(UserProfile.class));

        assertTrue(userProfileService.createUserProfile(EXPECTED_PROFILE_DTO));
    }

    @Test
    public void createUserProfileWhenProfileDtoIsNull() {
        doNothing().when(profileRepository).create(any(UserProfile.class));

        assertFalse(userProfileService.createUserProfile(null));
    }

    @Test
    public void updateUserProfileWithoutErrors() {
        UserProfile actualUserProfile = copyProfile();
        actualUserProfile.setFullName("Ivan");

        doReturn(actualUserProfile).when(profileRepository).read(ID);
        doReturn(EXPECTED_USER).when(userService).loadUserByUsername(USERNAME);
        doNothing().when(profileRepository).update(any(UserProfile.class));

        assertTrue(userProfileService.updateUserProfile(ID, EXPECTED_PROFILE_DTO));
        assertEquals(EXPECTED_USER_PROFILE, actualUserProfile);
    }

    @Test
    public void updateUserProfileWhenProfileDtoIsNull() {
        doReturn(EXPECTED_USER_PROFILE).when(profileRepository).read(ID);

        assertFalse(userProfileService.updateUserProfile(ID, null));
    }

    @Test
    public void updateUserProfileWhenUserProfileNotInDatabase() {
        doReturn(null).when(profileRepository).read(ID);

        assertFalse(userProfileService.updateUserProfile(ID, EXPECTED_PROFILE_DTO));
    }

    @Test
    public void deleteUserProfileWithoutErrors() {
        doReturn(EXPECTED_USER_PROFILE).when(profileRepository).read(ID);
        doNothing().when(profileRepository).delete(any(UserProfile.class));

        assertTrue(userProfileService.deleteUserProfile(ID));
    }

    @Test
    public void deleteUserProfileWhenUserProfileNotInDatabase() {
        doReturn(null).when(profileRepository).read(ID);

        assertFalse(userProfileService.deleteUserProfile(ID));
    }

    @Test
    public void getProfileDtoByUsernameWithoutErrors() {
        doReturn(EXPECTED_USER).when(userService).loadUserByUsername(USERNAME);
        doReturn(EXPECTED_USER_PROFILE).when(profileRepository).findByUser(EXPECTED_USER);

        ProfileDto actualProfileDto = userProfileService.getProfileDtoByUsername(USERNAME);
        assertEquals(EXPECTED_PROFILE_DTO, actualProfileDto);
    }

    @Test
    public void getProfileByUsernameWhenUserWithCurrentUsernameNotInDatabase() {
        doReturn(null).when(userService).loadUserByUsername(USERNAME);

        assertNull(userProfileService.getProfileByUsername(USERNAME));
    }

    @Test
    public void getUserProfileByIdWithoutErrors() {
        doReturn(EXPECTED_USER_PROFILE).when(profileRepository).read(ID);

        ProfileDto actualProfileDto = userProfileService.getProfileById(ID);
        assertEquals(EXPECTED_PROFILE_DTO, actualProfileDto);
    }

    @Test
    public void getLessonListForUserWithoutErrors() {
        doReturn(EXPECTED_USER).when(userService).loadUserByUsername(USERNAME);
        doReturn(EXPECTED_USER_PROFILE).when(profileRepository).findByUser(EXPECTED_USER);

        List<LessonDto> actualLessonDtoList = userProfileService.getLessonListForUser(USERNAME);
        assertEquals(EXPECTED_LESSON_DTO_LIST, actualLessonDtoList);
    }

    @Test
    public void getLessonListForUserWhenUserWithCurrentUsernameNotInDatabase() {
        doReturn(null).when(userService).loadUserByUsername(USERNAME);

        assertNull(userProfileService.getLessonListForUser(USERNAME));
    }

    @Test
    public void getCourseListForUserWithoutErrors() {
        doReturn(EXPECTED_USER).when(userService).loadUserByUsername(USERNAME);
        doReturn(EXPECTED_USER_PROFILE).when(profileRepository).findByUser(EXPECTED_USER);

        List<CourseDto> actualCourseDtoList = userProfileService.getCourseListForUser(USERNAME);
        assertEquals(EXPECTED_COURSE_DTO_LIST, actualCourseDtoList);
    }

    @Test
    public void getCourseListForUserWhenUserWithCurrentUsernameNotInDatabase() {
        doReturn(null).when(userService).loadUserByUsername(USERNAME);

        assertNull(userProfileService.getCourseListForUser(USERNAME));
    }

    private UserProfile copyProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(EXPECTED_USER_PROFILE.getId());
        userProfile.setUser(EXPECTED_USER_PROFILE.getUser());
        userProfile.setFullName(EXPECTED_USER_PROFILE.getFullName());
        userProfile.setPlaceOfWork(EXPECTED_USER_PROFILE.getPlaceOfWork());
        userProfile.setLessons(EXPECTED_USER_PROFILE.getLessons());
        userProfile.setCourses(EXPECTED_USER_PROFILE.getCourses());
        return userProfile;
    }
}
