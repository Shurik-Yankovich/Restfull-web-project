package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.dto.SectionDto;
import com.expexchangeservice.model.dto.ThemeDto;
import com.expexchangeservice.model.entities.Theme;

import java.util.List;

public interface IDictionaryService {

    List<Theme> getThemeDictionary();
    List<Theme> getThemeDictionaryOnTheSection(Long sectionId);
    List<SectionDto> getSectionDictionary();
    Theme getThemeById(Long themeId);
    SectionDto getSectionById(Long sectionId);
    boolean updateTheme(Long themeId, ThemeDto themeDto);
    boolean updateSection(SectionDto section);
    boolean createTheme(Long sectionId, ThemeDto themeDto);
    boolean createSection(SectionDto section);
    boolean deleteTheme(Long sectionId, Long themeId);
    boolean deleteSection(Long sectionId);
}
