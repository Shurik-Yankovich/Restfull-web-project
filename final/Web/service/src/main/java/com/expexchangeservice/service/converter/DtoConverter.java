package com.expexchangeservice.service.converter;

import com.expexchangeservice.model.dto.*;
import com.expexchangeservice.model.entities.*;
import com.expexchangeservice.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DtoConverter {

    @Autowired
    private IUserService userService;

    public Lesson convertDtoToLesson(Lesson lesson, LessonDto lessonDto) {
        if (lessonDto == null || lesson == null) {
            return null;
        }
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

    public LessonDto convertLessonToDto(Lesson lesson) {
        if (lesson == null) {
            return null;
        }
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(lesson.getId());
        lessonDto.setTheme(lesson.getTheme());
        ProfileDto profileDto = convertUserProfileToDto(lesson.getProfessor());
        lessonDto.setProfessor(profileDto);
        lessonDto.setType(lesson.getType());
        lessonDto.setDate(lesson.getDate());
        return lessonDto;
    }

    public List<LessonDto> convertLessonListToDtoList(List<Lesson> lessons) {
        if (lessons == null) {
            return null;
        }
        List<LessonDto> lessonDtoList = new ArrayList<>();
        for (Lesson lesson : lessons) {
            lessonDtoList.add(convertLessonToDto(lesson));
        }
        return lessonDtoList;
    }

    public Course convertDtoToCourse(Course course, CourseDto courseDto) {
        if (courseDto == null || course == null) {
            return null;
        }
        course.setId(courseDto.getId());
        course.setSection(convertDtoToSection(new Section(), courseDto.getSection()));
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

    public CourseDto convertCourseToDto(Course course) {
        if (course == null) {
            return null;
        }
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setSection(convertSectionToDto(course.getSection()));
        ProfileDto profileDto = convertUserProfileToDto(course.getProfessor());
        courseDto.setProfessor(profileDto);
        courseDto.setType(course.getType());
        courseDto.setDateStart(course.getDateStart());
        courseDto.setCountLesson(course.getCountLesson());
        return courseDto;
    }

    public List<CourseDto> convertCourseListToDtoList(List<Course> courses) {
        if (courses == null) {
            return null;
        }
        List<CourseDto> courseDtoList = new ArrayList<>();
        for (Course course : courses) {
            courseDtoList.add(convertCourseToDto(course));
        }
        return courseDtoList;
    }

    public UserProfile convertDtoToUserProfile(UserProfile userProfile, ProfileDto profileDto) {
        if (profileDto == null || userProfile == null) {
            return null;
        }
        userProfile.setId(profileDto.getId());
        userProfile.setFullName(profileDto.getFullName());
        userProfile.setUser(userService.loadUserByUsername(profileDto.getUsername()));
        userProfile.setPlaceOfWork(profileDto.getPlaceOfWork());
        return userProfile;
    }

    public ProfileDto convertUserProfileToDto(UserProfile userProfile) {
        if (userProfile == null) {
            return null;
        }
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(userProfile.getId());
        profileDto.setFullName(userProfile.getFullName());
        profileDto.setUsername(userProfile.getUser().getUsername());
        profileDto.setPlaceOfWork(userProfile.getPlaceOfWork());
        return profileDto;
    }

    public SectionDto convertSectionToDto(Section section) {
        SectionDto sectionDto = new SectionDto();
        sectionDto.setId(section.getId());
        sectionDto.setTitle(section.getTitle());
        return sectionDto;
    }

    public Section convertDtoToSection(Section section, SectionDto sectionDto) {
        if (section == null || sectionDto == null) {
            return null;
        }
        section.setId(sectionDto.getId());
        section.setTitle(sectionDto.getTitle());
        return section;
    }

    public List<SectionDto> convertSectionListToDtoList(List<Section> sections) {
        if (sections == null) {
            return null;
        }
        List<SectionDto> sectionDtoList = new ArrayList<>();
        for (Section section : sections) {
            sectionDtoList.add(convertSectionToDto(section));
        }
        return sectionDtoList;
    }

}
