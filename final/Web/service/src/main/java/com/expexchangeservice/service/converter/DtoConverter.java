package com.expexchangeservice.service.converter;

import com.expexchangeservice.model.dto.CourseDto;
import com.expexchangeservice.model.dto.LessonDto;
import com.expexchangeservice.model.dto.ProfileDto;
import com.expexchangeservice.model.dto.UserDto;
import com.expexchangeservice.model.entities.Course;
import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class DtoConverter {

    @Autowired
    private static IUserService USER_SERVICE;

    public static Lesson convertDtoToLesson(Lesson lesson, LessonDto lessonDto) {
        lesson.setId(lessonDto.getId());
        lesson.setTheme(lessonDto.getTheme());
        UserProfile profile = lesson.getProfessor();
        if (lesson.getProfessor() == null) {
            lesson.setProfessor(convertDtoToUserProfile(new UserProfile(), lessonDto.getProfessor()));
        } else {
            lesson.setProfessor(convertDtoToUserProfile(lesson.getProfessor(), lessonDto.getProfessor()));
        }
        lesson.setType(lessonDto.getType());
        lesson.setDate(lessonDto.getDate());
        return lesson;
    }

    public static LessonDto convertLessonToDto(Lesson lesson) {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(lesson.getId());
        lessonDto.setTheme(lesson.getTheme());
        ProfileDto profileDto = convertUserProfileToDto(lesson.getProfessor());
        lessonDto.setProfessor(profileDto);
        lessonDto.setType(lesson.getType());
        lessonDto.setDate(lesson.getDate());
        return lessonDto;
    }

    public static List<LessonDto> convertLessonListToDtoList(List<Lesson> lessons) {
        List<LessonDto> lessonDtoList = new ArrayList<>();
        for (Lesson lesson : lessons) {
            lessonDtoList.add(convertLessonToDto(lesson));
        }
        return lessonDtoList;
    }

    public static Course convertDtoToCourse(Course course, CourseDto courseDto) {
        course.setId(courseDto.getId());
        course.setSection(courseDto.getSection());
        if (course.getProfessor() == null) {
            course.setProfessor(convertDtoToUserProfile(new UserProfile(), courseDto.getProfessor()));
        } else {
            course.setProfessor(convertDtoToUserProfile(course.getProfessor(), courseDto.getProfessor()));
        }
        course.setType(courseDto.getType());
        course.setDateStart(courseDto.getDateStart());
        course.setCountLesson(courseDto.getCountLesson());
        return course;
    }

    public static CourseDto convertCourseToDto(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setSection(course.getSection());
        ProfileDto profileDto = convertUserProfileToDto(course.getProfessor());
        courseDto.setProfessor(profileDto);
        courseDto.setType(course.getType());
        courseDto.setDateStart(course.getDateStart());
        courseDto.setCountLesson(course.getCountLesson());
        return courseDto;
    }

    public static List<CourseDto> convertCourseListToDtoList(List<Course> courses) {
        List<CourseDto> courseDtoList = new ArrayList<>();
        for (Course course : courses) {
            courseDtoList.add(convertCourseToDto(course));
        }
        return courseDtoList;
    }

    public static UserProfile convertDtoToUserProfile(UserProfile userProfile, ProfileDto profileDto) {
        userProfile.setId(userProfile.getId());
        userProfile.setFullName(profileDto.getFullName());
        userProfile.setUser(USER_SERVICE.loadUserByUsername(profileDto.getUsername()));
        userProfile.setPlaceOfWork(profileDto.getPlaceOfWork());
        return userProfile;
    }

    public static ProfileDto convertUserProfileToDto(UserProfile userProfile) {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(userProfile.getId());
        profileDto.setFullName(userProfile.getFullName());
        profileDto.setUsername(userProfile.getUser().getUsername());
        profileDto.setPlaceOfWork(userProfile.getPlaceOfWork());
        return profileDto;
    }

    public static UserDto convertUserToDto(User user) {
        if (user != null) {
            UserDto userDto = new UserDto();
            userDto.setUsername(user.getUsername());
            userDto.setPassword(user.getPassword());
            return userDto;
        }
        return null;
    }

    public static User convertDtoToUser(UserDto userDto) {
        if (userDto != null) {
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());
//            user.setPasswordConfirm(userRegistrationDto.getPasswordConfirm());
            return user;
        }
        return null;
    }

}
