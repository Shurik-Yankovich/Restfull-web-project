package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.dto.ThemeDto;
import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.model.exception.DBException;

import java.util.List;

public interface IDictionaryService {

    List<Theme> getThemeDictionary();
    List<Theme> getThemeDictionaryOnTheSection(Integer sectionId);
    List<Section> getSectionDictionary();
    Theme getThemeById(Integer themeId);
    Section getSectionById(Integer sectionId);
    void updateTheme(int id, ThemeDto themeDto);
    void updateSection(Section section);
    void createTheme(int sectionId, ThemeDto themeDto);
    void createSection(Section section);
    void deleteTheme(Integer sectionId, Integer themeId);
    void deleteSection(Integer sectionId);
}
