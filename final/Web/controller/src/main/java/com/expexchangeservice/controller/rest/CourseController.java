package com.expexchangeservice.controller.rest;

import com.expexchangeservice.model.dto.CourseDto;
import com.expexchangeservice.model.dto.DateDto;
import com.expexchangeservice.model.dto.RequestError;
import com.expexchangeservice.model.entities.Review;
import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.service.interfaces.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/courses")
public class CourseController {

    private ICourseService courseService;

    @Autowired
    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createCourse(@RequestBody CourseDto courseDto) {

        try {
            courseService.addCourse(courseDto);
            return  new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "course not added",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getCoursesList() {
        try {
            List<CourseDto> courses = courseService.getAll();
            return courses != null
                    ? new ResponseEntity<>(courses, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "courses not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{courseId}")
    public ResponseEntity<?> changeCourse(@PathVariable(name = "courseId") int courseId,
                                          @RequestBody CourseDto lesson) {
        try {
            courseService.changeCourse(courseId, lesson);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "course not changed",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable(name = "courseId") int courseId) {
        try {
            courseService.deleteCourse(courseId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "course not deleted",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable(name = "courseId") int courseId) {
        try {
            CourseDto course = courseService.getCourseById(courseId);
            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "course not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/date")
    public ResponseEntity<?> getCoursesOnTheDate(@RequestBody DateDto dateDto) {
        try {
            List<CourseDto> courses = courseService.getCoursesOnTheDate(dateDto.getDate());
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "courses not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/afterdate")
    public ResponseEntity<?> getCoursesAfterDate(@RequestBody DateDto dateDto) {
        try {
            List<CourseDto> courses = courseService.getCoursesAfterDate(dateDto.getDate());
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "courses not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/available")
    public ResponseEntity<?> getListOfAvailableLessons() {
        try {
            List<CourseDto> courses = courseService.getCoursesAfterDate(LocalDate.now());
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "courses not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/section")
    public ResponseEntity<?> getCoursesOnTheTheme(@RequestBody Section section) {
        try {
            List<CourseDto> courses = courseService.getCoursesOnTheSection(section);
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "courses not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/professor")
    public ResponseEntity<?> getCoursesForTheProfessor(@RequestBody UserProfile professor) {
        try {
            List<CourseDto> courses = courseService.getCoursesForTheProfessor(professor);
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "courses not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/type/{type}")
    public ResponseEntity<?> getCoursesByType(@PathVariable(name = "type") String type) {
        try {
            List<CourseDto> courses = courseService.getCoursesByType(Type.valueOf(type.toUpperCase()));
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "courses not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{courseId}/review")
    public ResponseEntity<?> getReviewOnTheCourse(@PathVariable(name = "courseId") int courseId) {
        try {
            Set<Review> reviews = courseService.getReviewOnTheLesson(courseId);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "reviews not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/{courseId}/review")
    public ResponseEntity<?> addReviewToTheCourse(@PathVariable(name = "courseId") int courseId,
                                                  @RequestBody Review review) {
        try {
            courseService.addReview(courseId, review);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "review not added",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
