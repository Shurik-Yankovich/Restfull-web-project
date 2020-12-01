package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.dto.CourseDto;
import com.expexchangeservice.model.dto.LessonDto;
import com.expexchangeservice.model.dto.ProfileDto;
import com.expexchangeservice.model.entities.Course;
import com.expexchangeservice.model.entities.Lesson;

import java.util.List;

public interface IUserProfileService {

    void addUserProfile(ProfileDto profileDto);
    boolean changeUserProfile(int profileId, ProfileDto profileDto);
    boolean deleteUserProfile(int profileId);
    ProfileDto getProfileDtoByUsername(String username);
    boolean signUpForTheLesson(String username, Lesson lesson);
    boolean signUpForTheCourse(String username, Course course);
    ProfileDto getUserProfileById(Integer profileId);
    List<LessonDto> getLessonListForUser(String username);
    List<CourseDto> getCourseListForUser(String username);
    boolean changeUserRole(String username, boolean isAdmin);
}
