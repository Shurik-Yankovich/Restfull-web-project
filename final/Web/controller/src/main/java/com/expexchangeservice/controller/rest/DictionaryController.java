package com.expexchangeservice.controller.rest;

import com.expexchangeservice.model.dto.RequestError;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.model.exception.DBException;
import com.expexchangeservice.service.interfaces.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/dictionary")
public class DictionaryController {

    private IDictionaryService dictionaryService;

    @Autowired
    public DictionaryController(IDictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PostMapping(value = "/theme")
    public ResponseEntity<?> createTheme(@RequestBody Theme theme) {
        try {
            dictionaryService.addTheme(theme);
            return  new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DBException e) {
            return new ResponseEntity<>(new RequestError(400,
                    "theme not added",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/theme")
    public ResponseEntity<?> getThemeList() {
        try {
            List<Theme> themes = dictionaryService.getThemeDictionary();
            return  themes != null
                    ? new ResponseEntity<>(themes, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DBException e) {
            return new ResponseEntity<>(new RequestError(400,
                    "theme not added",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
