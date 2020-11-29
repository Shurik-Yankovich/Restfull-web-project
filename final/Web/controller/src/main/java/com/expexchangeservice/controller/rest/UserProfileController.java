package com.expexchangeservice.controller.rest;

import com.expexchangeservice.model.dto.*;
import com.expexchangeservice.model.entities.Course;
import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.service.interfaces.IUserProfileService;
import com.expexchangeservice.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/profile")
public class UserProfileController {

    private IUserProfileService profileService;
    private IUserService userService;

    @Autowired
    public UserProfileController(IUserProfileService profileService, IUserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createProfile(@RequestBody ProfileDto profile) {
        profileService.addUserProfile(profile);
        return new ResponseEntity<>(HttpStatus.CREATED);
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
                    "profile not found",
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

    @PostMapping(value = "/user/password/change")
    public ResponseEntity<?> changePassword(@RequestBody UserCreds userCreds) {
        if (!userCreds.getNewPassword().equals(userCreds.getPasswordConfirm())) {
            return new ResponseEntity<>(new RequestError(400,
                    "passwords not match",
                    "New password does not match password confirmation"),
                    HttpStatus.BAD_REQUEST);
        }
        if (userService.changeUserPassword(userCreds)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new RequestError(400,
                    "incorrect username or password",
                    "Incorrect username or password. Please check your username and password"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/user/{username}/role")
    public ResponseEntity<?> changeRole(@PathVariable(name = "username") String username,
                                        @RequestParam boolean isAdmin) {
        if (profileService.changeUserRole(username, isAdmin)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new RequestError(400,
                    "incorrect username",
                    "Incorrect username. Please check your username"),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
