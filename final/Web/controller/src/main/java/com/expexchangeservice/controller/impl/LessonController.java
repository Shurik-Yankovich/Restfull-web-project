package com.expexchangeservice.controller.impl;

import com.expexchangeservice.service.interfaces.ILessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/lesson")
public class LessonController {

    private ILessonService lessonService;

    @Autowired
    public LessonController(ILessonService lessonService) {
        this.lessonService = lessonService;
    }


}
