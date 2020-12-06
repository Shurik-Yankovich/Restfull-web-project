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

import java.util.List;

@Service
public class UserProfileService implements IUserProfileService {

    private IUserProfileRepository profileRepository;
    private IUserService userService;
    private DtoConverter converter;

    @Autowired
    public UserProfileService(IUserProfileRepository profileRepository, IUserService userService,
                              DtoConverter converter) {
        this.profileRepository = profileRepository;
        this.userService = userService;
        this.converter = converter;
    }

    @Override
    public boolean createUserProfile(ProfileDto profileDto) {
        if (profileDto == null) {
            return false;
        }
        UserProfile userProfile = converter.convertDtoToUserProfile(new UserProfile(), profileDto);
        profileRepository.create(userProfile);
        return true;
    }

    @Override
    public boolean updateUserProfile(Long profileId, ProfileDto profileDto) {
        UserProfile userProfile = converter.convertDtoToUserProfile(profileRepository.read(profileId), profileDto);
        if (userProfile == null) {
            return false;
        }
        profileRepository.update(converter.convertDtoToUserProfile(userProfile, profileDto));
        return true;
    }

    @Override
    public boolean deleteUserProfile(Long profileId) {
        UserProfile userProfile = profileRepository.read(profileId);
        if (userProfile == null) {
            return false;
        }
        profileRepository.delete(userProfile);
        return true;
    }

    @Override
    public ProfileDto getProfileDtoByUsername(String username) {
        UserProfile userProfile = getProfileByUsername(username);
        return converter.convertUserProfileToDto(userProfile);
    }

    @Override
    public UserProfile getProfileByUsername(String username) {
        User user = userService.loadUserByUsername(username);
        return user == null ? null : profileRepository.findByUser(user);
    }

//    @Override
//    public boolean signUpForTheLesson(String username, LessonDto lessonDto) {
//        UserProfile profile = getProfileByUsername(username);
//        if (profile == null) {
//            return false;
//        }
//        profile.getLessons().add(lessonDto);
//        profileRepository.update(profile);
//        return true;
//    }

//    @Override
//    public boolean signUpForTheCourse(String username, CourseDto courseDto) {
//        UserProfile profile = getProfileByUsername(username);
//        if (profile == null) {
//            return false;
//        }
//        profile.getCourses().add(courseDto);
//        profileRepository.update(profile);
//        return true;
//    }

    @Override
    public ProfileDto getProfileById(Long profileId) {
        UserProfile profile = profileRepository.read(profileId);
        return converter.convertUserProfileToDto(profile);
    }

    @Override
    public List<LessonDto> getLessonListForUser(String username) {
        UserProfile profile = getProfileByUsername(username);
        if (profile == null) {
            return null;
        }
        List<Lesson> lessons = profile.getLessons();
        if (lessons.size() > 0) {
            lessons.sort(new LessonDateComparator());
        }
        return converter.convertLessonListToDtoList(lessons);
    }

    @Override
    public List<CourseDto> getCourseListForUser(String username) {
        UserProfile profile = getProfileByUsername(username);
        if (profile == null) {
            return null;
        }
        List<Course> courses = profile.getCourses();
        if (courses.size() > 0) {
            courses.sort(new CourseStartDateComparator());
        }
        return converter.convertCourseListToDtoList(courses);
    }

//    @Override
//    public boolean changeUserRole(String username, boolean isAdmin) {
//        UserProfile profile = getProfileByUsername(username);
//        User user = userService.changeUserRole(username, isAdmin);
//        if (user == null) {
//            return false;
//        }
//        profile.setUser(user);
//        profileRepository.update(profile);
//        return true;
//    }
}
