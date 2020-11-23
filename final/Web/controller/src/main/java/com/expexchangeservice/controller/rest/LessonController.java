package com.expexchangeservice.controller.rest;

import com.expexchangeservice.model.dto.RequestError;
import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.model.exception.DBException;
import com.expexchangeservice.service.interfaces.ILessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                    "lesson not changed",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
