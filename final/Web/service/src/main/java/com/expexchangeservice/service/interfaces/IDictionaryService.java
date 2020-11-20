package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.model.exception.DBException;

import java.util.List;

public interface IDictionaryService {

    List<Theme> getThemeDictionary() throws DBException;
//    List<Theme> getThemeDictionaryOnTheSection(Section section) throws DBException;
    List<Section> getSectionDictionary() throws DBException;
    void changeTheme(Theme theme) throws DBException;
    void changeSection(Section section) throws DBException;
    Theme addTheme(Theme theme) throws DBException;
    Section addSection(Section section) throws DBException;
    void deleteTheme(Integer themeId) throws DBException;
    void deleteSection(Integer sectionId) throws DBException;
}