package com.expexchangeservice.controller.rest;

import com.expexchangeservice.model.dto.DateDto;
import com.expexchangeservice.model.dto.LessonDto;
import com.expexchangeservice.model.dto.RequestError;
import com.expexchangeservice.model.entities.*;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.model.exception.DBException;
import com.expexchangeservice.service.interfaces.ILessonService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/lessons")
public class LessonController {

    private ILessonService lessonService;

    @Autowired
    public LessonController(ILessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createLesson(@RequestBody LessonDto lesson) {

        try {
            lessonService.addLesson(lesson);
            return  new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lesson not added",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getLessonsList() {
        try {
            List<LessonDto> lessons = lessonService.getAll();
            return lessons != null
                    ? new ResponseEntity<>(lessons, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lessons not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{lessonId}")
    public ResponseEntity<?> changeLesson(@PathVariable(name = "lessonId") int id,
                                          @RequestBody LessonDto lesson) {
        try {
            lessonService.changeLesson(id, lesson);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lesson not changed",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{lessonId}")
    public ResponseEntity<?> deleteLesson(@PathVariable(name = "lessonId") int lessonId) {
        try {
            lessonService.deleteLesson(lessonId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lesson not deleted",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{lessonId}")
    public ResponseEntity<?> getLessonById(@PathVariable(name = "lessonId") int lessonId) {
        try {
            LessonDto lesson = lessonService.getLessonById(lessonId);
            return new ResponseEntity<>(lesson, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lesson not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/date")
//    public ResponseEntity<?> getLessonsOnTheDate(@PathVariable(name = "date") String stringDate) {
    public ResponseEntity<?> getLessonsOnTheDate(@RequestBody DateDto dateDto) {
        try {
//            LocalDate date = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            List<LessonDto> lessons = lessonService.getLessonsOnTheDate(dateDto.getDate());
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lessons not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/afterdate")
    public ResponseEntity<?> getLessonsAfterDate(@RequestBody DateDto dateDto) {
        try {
            List<LessonDto> lessons = lessonService.getLessonsAfterDate(dateDto.getDate());
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lessons not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/theme")
    public ResponseEntity<?> getLessonsOnTheTheme(@RequestBody Theme theme) {
        try {
            List<LessonDto> lessons = lessonService.getLessonsOnTheTheme(theme);
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lessons not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/professor")
    public ResponseEntity<?> getLessonsForTheProfessor(@RequestBody UserProfile professor) {
        try {
            List<LessonDto> lessons = lessonService.getLessonsForTheProfessor(professor);
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lessons not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/type/{type}")
    public ResponseEntity<?> getLessonsByType(@PathVariable(name = "type") String type) {
        try {
            List<LessonDto> lessons = lessonService.getLessonsByType(Type.valueOf(type.toUpperCase()));
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lessons not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{lessonId}/review")
    public ResponseEntity<?> getReviewOnTheLesson(@PathVariable(name = "lessonId") int lessonId) {
        try {
            Set<Review> reviews = lessonService.getReviewOnTheLesson(lessonId);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "reviews not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/{lessonId}/review")
    public ResponseEntity<?> addReviewToTheLesson(@PathVariable(name = "lessonId") int lessonId,
                                                  @RequestBody Review review) {
        try {
            lessonService.addReview(lessonId, review);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "review not added",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
