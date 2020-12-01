package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.CourseDto;
import com.expexchangeservice.model.dto.LessonDto;
import com.expexchangeservice.model.dto.ProfileDto;
import com.expexchangeservice.model.entities.Course;
import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.repository.interfaces.IUserProfileRepository;
import com.expexchangeservice.service.converter.DtoConverter;
import com.expexchangeservice.service.interfaces.IUserService;
import com.expexchangeservice.service.interfaces.IUserProfileService;
import com.expexchangeservice.utils.comparators.CourseStartDateComparator;
import com.expexchangeservice.utils.comparators.LessonDateComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional
public class UserProfileService implements IUserProfileService {

    private IUserProfileRepository profileRepository;
    private IUserService userService;

    @Autowired
    public UserProfileService(IUserProfileRepository profileRepository, IUserService userService) {
        this.profileRepository = profileRepository;
        this.userService = userService;
    }

    @Override
    public void addUserProfile(ProfileDto profileDto) {
        UserProfile userProfile = DtoConverter.convertDtoToUserProfile(new UserProfile(), profileDto);
        profileRepository.create(userProfile);
    }

    @Override
    public boolean changeUserProfile(int profileId, ProfileDto profileDto) {
        UserProfile userProfile = DtoConverter.convertDtoToUserProfile(profileRepository.read(profileId), profileDto);
        if (userProfile == null) {
            return false;
        }
        profileRepository.update(DtoConverter.convertDtoToUserProfile(userProfile, profileDto));
        return true;
    }

    @Override
    public boolean deleteUserProfile(int profileId) {
        UserProfile userProfile = profileRepository.read(profileId);
        if (userProfile == null) {
            return false;
        }
        profileRepository.delete(userProfile);
        return true;
    }

    @Override
    public ProfileDto getProfileDtoByUsername(String username) {
        UserProfile userProfile = findProfileByUsername(username);
        return DtoConverter.convertUserProfileToDto(userProfile);
    }

    private UserProfile findProfileByUsername(String username) {
        User user = userService.loadUserByUsername(username);
        return user == null ? null : profileRepository.findByUser(user);
    }

    @Override
    public boolean signUpForTheLesson(String username, Lesson lesson) {
        UserProfile profile = findProfileByUsername(username);
        if (profile == null) {
            return false;
        }
        profile.getLessons().add(lesson);
        profileRepository.update(profile);
        return true;
    }

    @Override
    public boolean signUpForTheCourse(String username, Course course) {
        UserProfile profile = findProfileByUsername(username);
        if (profile == null) {
            return false;
        }
        profile.getCourses().add(course);
        profileRepository.update(profile);
        return true;
    }

    @Override
    public ProfileDto getUserProfileById(Integer profileId) {
        UserProfile profile = profileRepository.read(profileId);
        return DtoConverter.convertUserProfileToDto(profile);
    }

    @Override
    public List<LessonDto> getLessonListForUser(String username) {
        UserProfile profile = findProfileByUsername(username);
        if (profile == null) {
            return null;
        }
        List<Lesson> lessons = profile.getLessons();
        if (lessons.size() > 0) {
            lessons.sort(new LessonDateComparator());
        }
        return DtoConverter.convertLessonListToDtoList(lessons);
    }

    @Override
    public List<CourseDto> getCourseListForUser(String username) {
        UserProfile profile = findProfileByUsername(username);
        if (profile == null) {
            return null;
        }
        List<Course> courses = profile.getCourses();
        if (courses.size() > 0) {
            courses.sort(new CourseStartDateComparator());
        }
        return DtoConverter.convertCourseListToDtoList(courses);
    }

    @Override
    public boolean changeUserRole(String username, boolean isAdmin) {
        UserProfile profile = findProfileByUsername(username);
        User user = userService.changeUserRole(username, isAdmin);
        if (user == null) {
            return false;
        }
        profile.setUser(user);
        profileRepository.update(profile);
        return true;
    }
}
