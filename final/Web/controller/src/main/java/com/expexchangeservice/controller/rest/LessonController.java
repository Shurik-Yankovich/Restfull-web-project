package com.expexchangeservice.controller.rest;

import com.expexchangeservice.model.dto.RequestError;
import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.model.exception.DBException;
import com.expexchangeservice.service.interfaces.ILessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
