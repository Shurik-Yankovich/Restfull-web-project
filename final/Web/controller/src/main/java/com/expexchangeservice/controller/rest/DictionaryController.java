package com.expexchangeservice.controller.rest;

import com.expexchangeservice.model.dto.RequestError;
import com.expexchangeservice.model.dto.ThemeDto;
import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.Theme;
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

    @GetMapping(value = "/theme")
    public ResponseEntity<?> getThemesList() {
        try {
            List<Theme> themes = dictionaryService.getThemeDictionary();
            return themes != null
                    ? new ResponseEntity<>(themes, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "themes not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/section")
    public ResponseEntity<?> createSection(@RequestBody Section section) {
        try {
            dictionaryService.createSection(section);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "section not added",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/section")
    public ResponseEntity<?> getSectionsList() {
        try {
            List<Section> sections = dictionaryService.getSectionDictionary();
            return sections != null
                    ? new ResponseEntity<>(sections, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "sections not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/section/{id}")
    public ResponseEntity<?> changeSection(@PathVariable(name = "id") int id, @RequestBody Section section) {
        try {
            dictionaryService.updateSection(section);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "section not changed",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/section/{id}")
    public ResponseEntity<?> deleteSection(@PathVariable(name = "id") int id) {
        try {
            dictionaryService.deleteSection(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "section not deleted",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/section/{id}/themes")
    public ResponseEntity<?> getThemesListBySection(@PathVariable(name = "id") int id) {
        try {
            List<Theme> themes = dictionaryService.getThemeDictionaryOnTheSection(id);
            return themes != null
                    ? new ResponseEntity<>(themes, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "sections not read",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/section/{id}/theme")
    public ResponseEntity<?> createTheme(@PathVariable(name = "id") int id, @RequestBody ThemeDto theme) {
        try {
//            Section section = dictionaryService.getSectionById(id);
            dictionaryService.createTheme(id,theme);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "theme not added",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/theme/{id}")
    public ResponseEntity<?> changeTheme(@PathVariable(name = "id") int id, @RequestBody ThemeDto theme) {
        try {
            dictionaryService.updateTheme(id, theme);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "theme not changed",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/section/{idSection}/theme/{id}")
    public ResponseEntity<?> deleteTheme(@PathVariable(name = "idSection") int idSection,
                                         @PathVariable(name = "id") int idTheme) {
        try {
            dictionaryService.deleteTheme(idSection, idTheme);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RequestError(400,
                    "theme not deleted",
                    e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
