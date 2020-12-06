package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.dto.CourseDto;
import com.expexchangeservice.model.dto.LessonDto;
import com.expexchangeservice.model.dto.ProfileDto;
import com.expexchangeservice.model.entities.Course;
import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.model.entities.UserProfile;

import java.util.List;

public interface IUserProfileService {

    boolean createUserProfile(ProfileDto profileDto);
    boolean updateUserProfile(Long profileId, ProfileDto profileDto);
    boolean deleteUserProfile(Long profileId);
    ProfileDto getProfileDtoByUsername(String username);
    UserProfile getProfileByUsername(String username);
//    boolean signUpForTheLesson(String username, LessonDto lessonDto);
//    boolean signUpForTheCourse(String username, CourseDto courseDto);
    ProfileDto getProfileById(Long profileId);
    List<LessonDto> getLessonListForUser(String username);
    List<CourseDto> getCourseListForUser(String username);
//    boolean changeUserRole(String username, boolean isAdmin);
}
