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
        List<Theme> themes = dictionaryService.getThemeDictionary();
        return themes != null ? new ResponseEntity<>(themes, HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "themes not found",
                        "themes not found"),
                        HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/section")
    public ResponseEntity<?> createSection(@RequestBody Section section) {
        dictionaryService.createSection(section);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/section")
    public ResponseEntity<?> getSectionsList() {
        List<Section> sections = dictionaryService.getSectionDictionary();
        return sections != null ? new ResponseEntity<>(sections, HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "sections not found",
                        "sections not found"),
                        HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/section/{sectionId}")
    public ResponseEntity<?> changeSection(@PathVariable(name = "sectionId") int sectionId, @RequestBody Section section) {
        boolean isChanged = dictionaryService.updateSection(section);
        return isChanged ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "section not found",
                        "section with current id not found"),
                        HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/section/{sectionId}")
    public ResponseEntity<?> deleteSection(@PathVariable(name = "sectionId") int sectionId) {
        boolean isDeleted = dictionaryService.deleteSection(sectionId);
        return isDeleted ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "section not found",
                        "section with current id not found"),
                        HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/section/{sectionId}/themes")
    public ResponseEntity<?> getThemesListBySection(@PathVariable(name = "sectionId") int sectionId) {
        List<Theme> themes = dictionaryService.getThemeDictionaryOnTheSection(sectionId);
        return themes != null ? new ResponseEntity<>(themes, HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "themes not found",
                        "themes for section with current id not found"),
                        HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/section/{sectionId}/theme")
    public ResponseEntity<?> createTheme(@PathVariable(name = "sectionId") int sectionId, @RequestBody ThemeDto theme) {
        dictionaryService.createTheme(sectionId, theme);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/theme/{themeId}")
    public ResponseEntity<?> changeTheme(@PathVariable(name = "themeId") int themeId, @RequestBody ThemeDto theme) {
        boolean isChanged = dictionaryService.updateTheme(themeId, theme);
        return isChanged ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "theme not found",
                        "theme with current id not found"),
                        HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/section/{idSection}/theme/{id}")
    public ResponseEntity<?> deleteTheme(@PathVariable(name = "idSection") int idSection,
                                         @PathVariable(name = "id") int idTheme) {
        boolean isDeleted = dictionaryService.deleteTheme(idSection, idTheme);
        return isDeleted ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(new RequestError(404,
                        "theme not found",
                        "theme with current id not found"),
                        HttpStatus.NOT_FOUND);
    }

}
