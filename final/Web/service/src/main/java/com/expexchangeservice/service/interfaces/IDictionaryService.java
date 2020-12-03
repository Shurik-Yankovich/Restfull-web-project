package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.dto.ThemeDto;
import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.Theme;

import java.util.List;

public interface IDictionaryService {

    List<Theme> getThemeDictionary();
    List<Theme> getThemeDictionaryOnTheSection(Long sectionId);
    List<Section> getSectionDictionary();
    Theme getThemeById(Long themeId);
    Section getSectionById(Long sectionId);
    boolean updateTheme(Long themeId, ThemeDto themeDto);
    boolean updateSection(Section section);
    void createTheme(Long sectionId, ThemeDto themeDto);
    void createSection(Section section);
    boolean deleteTheme(Long sectionId, Long themeId);
    boolean deleteSection(Long sectionId);
}
