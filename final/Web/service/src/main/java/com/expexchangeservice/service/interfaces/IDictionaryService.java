package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.model.exception.DBException;

import java.util.List;

public interface IDictionaryService {

    List<Theme> getThemeDictionary() throws DBException;
    List<Theme> getThemeDictionaryOnTheSection(Integer sectionId) throws DBException;
    List<Section> getSectionDictionary() throws DBException;
    Theme getThemeById(Integer themeId) throws DBException;
    Section getSectionById(Integer sectionId) throws DBException;
    void changeTheme(int id, String theme) throws DBException;
    void changeSection(Section section) throws DBException;
    void addTheme(Theme theme) throws DBException;
    void addSection(Section section) throws DBException;
    void deleteTheme(Integer sectionId, Integer themeId) throws DBException;
    void deleteSection(Integer sectionId) throws DBException;
}
