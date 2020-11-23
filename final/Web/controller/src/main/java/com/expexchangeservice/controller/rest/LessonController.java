package com.expexchangeservice.controller.rest;

import com.expexchangeservice.model.dto.DateDto;
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
    public ResponseEntity<?> createLesson(@RequestBody Lesson lesson) {

        try {
            lessonService.addLesson(lesson);
            return  new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DBException e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lesson not added",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getLessonsList() {
        try {
            List<Lesson> lessons = lessonService.getAll();
            return lessons != null
                    ? new ResponseEntity<>(lessons, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DBException e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lessons not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> changeLesson(@PathVariable(name = "id") int id, @RequestBody Lesson lesson) {
        try {
            lessonService.changeLesson(lesson);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DBException e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lesson not changed",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable(name = "id") int id) {
        try {
            lessonService.deleteLesson(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DBException e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lesson not deleted",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getLessonById(@PathVariable(name = "id") int id) {
        try {
            Lesson lesson = lessonService.getLessonById(id);
            return new ResponseEntity<>(lesson, HttpStatus.OK);
        } catch (DBException e) {
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
            List<Lesson> lessons = lessonService.getLessonsOnTheDate(dateDto.getDate());
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } catch (DBException e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lessons not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/afterdate")
    public ResponseEntity<?> getLessonsAfterDate(@RequestBody DateDto dateDto) {
        try {
            List<Lesson> lessons = lessonService.getLessonsAfterDate(dateDto.getDate());
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } catch (DBException e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lessons not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/theme")
    public ResponseEntity<?> getLessonsOnTheTheme(@RequestBody Theme theme) {
        try {
            List<Lesson> lessons = lessonService.getLessonsOnTheTheme(theme);
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } catch (DBException e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lessons not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/professor")
    public ResponseEntity<?> getLessonsForTheProfessor(@RequestBody UserProfile professor) {
        try {
            List<Lesson> lessons = lessonService.getLessonsForTheProfessor(professor);
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } catch (DBException e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lessons not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/type")
    public ResponseEntity<?> getLessonsByType(@RequestBody Type type) {
        try {
            List<Lesson> lessons = lessonService.getLessonsByType(type);
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } catch (DBException e) {
            return new ResponseEntity<>(new RequestError(400,
                    "lessons not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}/review")
    public ResponseEntity<?> getReviewOnTheLesson(@PathVariable(name = "id") int id) {
        try {
            Set<Review> reviews = lessonService.getReviewOnTheLesson(id);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (DBException e) {
            return new ResponseEntity<>(new RequestError(400,
                    "reviews not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/{id}/review")
    public ResponseEntity<?> addReviewToTheLesson(@PathVariable(name = "id") int id, @RequestBody Review review) {
        try {
            lessonService.addReview(id, review);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DBException e) {
            return new ResponseEntity<>(new RequestError(400,
                    "review not added",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
