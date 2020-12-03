package com.expexchangeservice.controller.rest;

import com.expexchangeservice.model.dto.*;
import com.expexchangeservice.model.entities.Course;
import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.service.interfaces.ITokenService;
import com.expexchangeservice.service.interfaces.IUserProfileService;
import com.expexchangeservice.service.interfaces.IUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/profile")
public class UserProfileController {

    private IUserProfileService profileService;
    private IUserService userService;
    private ITokenService tokenService;

    @Autowired
    public UserProfileController(IUserProfileService profileService,
                                 IUserService userService, ITokenService tokenService) {
        this.profileService = profileService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createProfile(@RequestBody ProfileDto profile,
                                           HttpServletRequest httpRequest) {
        if (!tokenService.checkUser(httpRequest, profile.getUsername())) {
            return new ResponseEntity<>(new RequestError(403,
                    "Hasn't access",
                    "Hasn't access with this user"),
                    HttpStatus.FORBIDDEN);
        }
        profileService.addUserProfile(profile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{profileId}")
    public ResponseEntity<?> changeProfile(@PathVariable(name = "profileId") long profileId,
                                           @RequestBody ProfileDto profile,
                                           HttpServletRequest httpRequest) {
        if (!tokenService.checkUser(httpRequest, profileId)) {
            return new ResponseEntity<>(new RequestError(403,
                    "Hasn't access",
                    "Hasn't access with this user"),
                    HttpStatus.FORBIDDEN);
        }
        boolean isChanged = profileService.changeUserProfile(profileId, profile);
        return isChanged ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "profiles not found",
                        "profile with current username not found"),
                        HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{profileId}")
    public ResponseEntity<?> deleteProfile(@PathVariable(name = "profileId") long profileId,
                                           HttpServletRequest httpRequest) {
        if (!tokenService.checkUser(httpRequest, profileId)) {
            return new ResponseEntity<>(new RequestError(403,
                    "Hasn't access",
                    "Hasn't access with this user"),
                    HttpStatus.FORBIDDEN);
        }
        tokenService.deleteToken(httpRequest);
        boolean isDeleted = profileService.deleteUserProfile(profileId);
        return isDeleted ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "profiles not found",
                        "profile with current username not found"),
                        HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Get profile by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Profile received successfully", response = ProfileDto.class),
            @ApiResponse(code = 400, message = "Invalid path variable", response = RequestError.class),
            @ApiResponse(code = 404, message = "Can't find profile", response = RequestError.class)
    })
    @GetMapping(value = "/{profileId}")
    public ResponseEntity<?> getProfileById(@PathVariable(name = "profileId") long profileId) {
        ProfileDto profile = profileService.getUserProfileById(profileId);
        return profile != null ? new ResponseEntity<>(profile, HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "profile not found",
                        "profile with current id not found"),
                        HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Get profile by username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Profile received successfully", response = ProfileDto.class),
            @ApiResponse(code = 400, message = "Invalid path variable", response = RequestError.class),
            @ApiResponse(code = 404, message = "Can't find profile", response = RequestError.class)
    })
    @GetMapping(value = "/user/{username}")
    public ResponseEntity<?> getProfileByUsername(@PathVariable(name = "username") String username) {
        ProfileDto profile = profileService.getProfileDtoByUsername(username);
        return profile != null ? new ResponseEntity<>(profile, HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "profile not found",
                        "profile with current username not found"),
                        HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Get lessons list for user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Profile received successfully", response = List.class),
            @ApiResponse(code = 400, message = "Invalid path variable", response = RequestError.class),
            @ApiResponse(code = 404, message = "Can't find profile", response = RequestError.class)
    })
    @GetMapping(value = "/user/{username}/lessons")
    public ResponseEntity<?> getLessonsForUser(@PathVariable(name = "username") String username,
                                               HttpServletRequest httpRequest) {
        if (!tokenService.checkUser(httpRequest, username)) {
            return new ResponseEntity<>(new RequestError(403,
                    "Hasn't access",
                    "Hasn't access with this user"),
                    HttpStatus.FORBIDDEN);
        }
        List<LessonDto> lessons = profileService.getLessonListForUser(username);
        return lessons != null ? new ResponseEntity<>(lessons, HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "profiles not found",
                        "profile with current username not found"),
                        HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Get courses list for user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Profile received successfully", response = List.class),
            @ApiResponse(code = 400, message = "Invalid path variable", response = RequestError.class),
            @ApiResponse(code = 404, message = "Can't find profile", response = RequestError.class)
    })
    @GetMapping(value = "/user/{username}/courses")
    public ResponseEntity<?> getCoursesForUser(@PathVariable(name = "username") String username,
                                               HttpServletRequest httpRequest) {
        if (!tokenService.checkUser(httpRequest, username)) {
            return new ResponseEntity<>(new RequestError(403,
                    "Hasn't access",
                    "Hasn't access with this user"),
                    HttpStatus.FORBIDDEN);
        }
        List<CourseDto> courses = profileService.getCourseListForUser(username);
        return courses != null ? new ResponseEntity<>(courses, HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "profiles not found",
                        "profile with current username not found"),
                        HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/user/{username}/lessons")
    public ResponseEntity<?> signUpForTheLesson(@PathVariable(name = "username") String username,
                                                @RequestBody Lesson lessonDto,
                                                HttpServletRequest httpRequest) {
        if (!tokenService.checkUser(httpRequest, username)) {
            return new ResponseEntity<>(new RequestError(403,
                    "Hasn't access",
                    "Hasn't access with this user"),
                    HttpStatus.FORBIDDEN);
        }
        boolean isSigned = profileService.signUpForTheLesson(username, lessonDto);
        return isSigned ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "profiles not found",
                        "profile with current username not found"),
                        HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/user/{username}/courses")
    public ResponseEntity<?> signUpForTheCourse(@PathVariable(name = "username") String username,
                                                @RequestBody Course courseDto,
                                                HttpServletRequest httpRequest) {
        if (!tokenService.checkUser(httpRequest, username)) {
            return new ResponseEntity<>(new RequestError(403,
                    "Hasn't access",
                    "Hasn't access with this user"),
                    HttpStatus.FORBIDDEN);
        }
        boolean isSigned = profileService.signUpForTheCourse(username, courseDto);
        return isSigned ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "profiles not found",
                        "profile with current username not found"),
                        HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/user/password/change")
    public ResponseEntity<?> changePassword(@RequestBody UserCreds userCreds,
                                            HttpServletRequest httpRequest) {
        if (!userCreds.getNewPassword().equals(userCreds.getPasswordConfirm())) {
            return new ResponseEntity<>(new RequestError(400,
                    "passwords not match",
                    "New password does not match password confirmation"),
                    HttpStatus.BAD_REQUEST);
        }
        if (!tokenService.checkUser(httpRequest, userCreds.getUser().getUsername())) {
            return new ResponseEntity<>(new RequestError(403,
                    "Hasn't access",
                    "Hasn't access with this user"),
                    HttpStatus.FORBIDDEN);
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

    @PutMapping(value = "/user/{username}/role")
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
