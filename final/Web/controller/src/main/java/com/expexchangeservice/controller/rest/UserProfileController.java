package com.expexchangeservice.controller.rest;

import com.expexchangeservice.model.dto.CourseDto;
import com.expexchangeservice.model.dto.LessonDto;
import com.expexchangeservice.model.dto.ProfileDto;
import com.expexchangeservice.model.dto.RequestError;
import com.expexchangeservice.model.entities.Course;
import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.service.interfaces.IUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/profile")
public class UserProfileController {

    private IUserProfileService profileService;

    @Autowired
    public UserProfileController(IUserProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createProfile(@RequestBody ProfileDto profile) {
        try {
            profileService.addUserProfile(profile);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "profile not added",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{profileId}")
    public ResponseEntity<?> changeProfile(@PathVariable(name = "profileId") int profileId,
                                           @RequestBody ProfileDto profile) {
        try {
            profileService.changeUserProfile(profileId, profile);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "profile not changed",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{profileId}")
    public ResponseEntity<?> deleteProfile(@PathVariable(name = "profileId") int profileId) {
        try {
            profileService.deleteUserProfile(profileId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "profile not deleted",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{profileId}")
    public ResponseEntity<?> getProfileById(@PathVariable(name = "profileId") int profileId) {
        try {
            ProfileDto profile = profileService.getUserProfileById(profileId);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "profile not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/user/{username}")
    public ResponseEntity<?> getProfileByUsername(@PathVariable(name = "username") String username) {
        try {
            ProfileDto profile = profileService.getProfileDtoByUsername(username);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "profile not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/user/{username}/lessons")
    public ResponseEntity<?> getLessonsForUser(@PathVariable(name = "username") String username) {
        try {
            List<LessonDto> lessons = profileService.getLessonListForUser(username);
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "profile not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/user/{username}/courses")
    public ResponseEntity<?> getCourseForUser(@PathVariable(name = "username") String username) {
        try {
            List<CourseDto> courses = profileService.getCourseListForUser(username);
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "profile not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/user/{username}/lessons")
    public ResponseEntity<?> signUpForTheLesson(@PathVariable(name = "username") String username,
                                                @RequestBody Lesson lessonDto) {
        try {
            profileService.signUpForTheLesson(username, lessonDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "profile not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/user/{username}/courses")
    public ResponseEntity<?> signUpForTheCourse(@PathVariable(name = "username") String username,
                                                @RequestBody Course courseDto) {
        try {
            profileService.signUpForTheCourse(username, courseDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "profile not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }


}
