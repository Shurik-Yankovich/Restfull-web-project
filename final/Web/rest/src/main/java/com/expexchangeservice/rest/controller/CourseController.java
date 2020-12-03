package com.expexchangeservice.rest.controller;

import com.expexchangeservice.model.dto.CourseDto;
import com.expexchangeservice.model.dto.DateDto;
import com.expexchangeservice.model.dto.ProfileDto;
import com.expexchangeservice.model.dto.RequestError;
import com.expexchangeservice.model.entities.Review;
import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.service.interfaces.ICourseService;
import com.expexchangeservice.service.interfaces.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/courses")
public class CourseController {

    private ICourseService courseService;
    private ITokenService tokenService;

    @Autowired
    public CourseController(ICourseService courseService, ITokenService tokenService) {
        this.courseService = courseService;
        this.tokenService = tokenService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createCourse(@RequestBody CourseDto courseDto,
                                          HttpServletRequest httpRequest) {
        if (!tokenService.checkUser(httpRequest, courseDto.getProfessor().getUsername())) {
            return new ResponseEntity<>(new RequestError(403,
                    "Hasn't access",
                    "Hasn't access with this user"),
                    HttpStatus.FORBIDDEN);
        }
        courseService.createCourse(courseDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getCoursesList() {
        List<CourseDto> courses = courseService.getAll();
        return courses != null ? new ResponseEntity<>(courses, HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "courses not found",
                        "courses not found"),
                        HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{courseId}")
    public ResponseEntity<?> changeCourse(@PathVariable(name = "courseId") long courseId,
                                          @RequestBody CourseDto courseDto,
                                          HttpServletRequest httpRequest) {
        if (!tokenService.checkUser(httpRequest, courseDto.getProfessor().getUsername())) {
            return new ResponseEntity<>(new RequestError(403,
                    "Hasn't access",
                    "Hasn't access with this user"),
                    HttpStatus.FORBIDDEN);
        }
        boolean isChanged = courseService.updateCourse(courseId, courseDto);
        return isChanged ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "course not found",
                        "course with current id not found"),
                        HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable(name = "courseId") long courseId,
                                          HttpServletRequest httpRequest) {
        CourseDto courseDto = courseService.getCourseById(courseId);
        if (!tokenService.checkUser(httpRequest, courseDto.getProfessor().getUsername())) {
            return new ResponseEntity<>(new RequestError(403,
                    "Hasn't access",
                    "Hasn't access with this user"),
                    HttpStatus.FORBIDDEN);
        }
        boolean isDeleted = courseService.deleteCourse(courseId);
        return isDeleted ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "course not found",
                        "course with current id not found"),
                        HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable(name = "courseId") long courseId) {
        CourseDto course = courseService.getCourseById(courseId);
        return course != null ? new ResponseEntity<>(course, HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "course not found",
                        "course with current id not found"),
                        HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/date")
    public ResponseEntity<?> getCoursesOnTheDate(@RequestBody DateDto dateDto) {
        List<CourseDto> courses = courseService.getCoursesOnTheDate(dateDto.getDate());
        return courses != null ? new ResponseEntity<>(courses, HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "courses not found",
                        "courses on the date not found"),
                        HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/afterdate")
    public ResponseEntity<?> getCoursesAfterDate(@RequestBody DateDto dateDto) {
        List<CourseDto> courses = courseService.getCoursesAfterDate(dateDto.getDate());
        return courses != null ? new ResponseEntity<>(courses, HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "courses not found",
                        "courses after the date not found"),
                        HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/available")
    public ResponseEntity<?> getListOfAvailableLessons() {
        List<CourseDto> courses = courseService.getCoursesAfterDate(LocalDate.now());
        return courses != null ? new ResponseEntity<>(courses, HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "courses not found",
                        "available courses not found"),
                        HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/section")
    public ResponseEntity<?> getCoursesOnTheTheme(@RequestBody Section section) {
        List<CourseDto> courses = courseService.getCoursesOnTheSection(section);
        return courses != null ? new ResponseEntity<>(courses, HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "courses not found",
                        "courses on the section not found"),
                        HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/professor")
    public ResponseEntity<?> getCoursesForTheProfessor(@RequestBody ProfileDto professor) {
        List<CourseDto> courses = courseService.getCoursesForTheProfessor(professor);
        return courses != null ? new ResponseEntity<>(courses, HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "courses not found",
                        "courses for the professor not found"),
                        HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/type/{type}")
    public ResponseEntity<?> getCoursesByType(@PathVariable(name = "type") String type) {
        List<CourseDto> courses = courseService.getCoursesByType(Type.valueOf(type.toUpperCase()));
        return courses != null ? new ResponseEntity<>(courses, HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "courses not found",
                        "courses by type not found"),
                        HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{courseId}/review")
    public ResponseEntity<?> getReviewOnTheCourse(@PathVariable(name = "courseId") long courseId) {
        Set<Review> reviews = courseService.getReviewOnTheLesson(courseId);
        return reviews != null ? new ResponseEntity<>(reviews, HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "reviews not found",
                        "reviews for course with current id not found"),
                        HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/{courseId}/review")
    public ResponseEntity<?> addReviewToTheCourse(@PathVariable(name = "courseId") long courseId,
                                                  @RequestBody Review review,
                                                  HttpServletRequest httpRequest) {
        if (!tokenService.checkUser(httpRequest, review.getUsername())) {
            return new ResponseEntity<>(new RequestError(406,
                    "can't add review",
                    "cannot add a review not under your username"),
                    HttpStatus.FORBIDDEN);
        }
        boolean isAdded = courseService.addReview(courseId, review);
        return isAdded ? new ResponseEntity<>(HttpStatus.CREATED) :
                new ResponseEntity<>(new RequestError(404,
                        "course not found",
                        "course with current id not found"),
                        HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{courseId}/reward")
    public ResponseEntity<?> changeRewardForCourse(@PathVariable(name = "courseId") long courseId,
                                                   @RequestParam int reward) {
        boolean isChanged = courseService.changeRewardByCourseId(courseId, reward);
        return isChanged ? new ResponseEntity<>(HttpStatus.CREATED) :
                new ResponseEntity<>(new RequestError(404,
                        "course not found",
                        "course with current id not found"),
                        HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/reward")
    public ResponseEntity<?> getRewardForCoursesForProfessor(HttpServletRequest httpRequest) {
        String username = tokenService.getUsernameByRequest(httpRequest);
        int reward = courseService.getRewardForCoursesByProfessor(username);
        return new ResponseEntity<>(reward, HttpStatus.CREATED);
    }
}
