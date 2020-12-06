package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.SectionDto;
import com.expexchangeservice.model.dto.ThemeDto;
import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.repository.interfaces.ISectionRepository;
import com.expexchangeservice.repository.interfaces.IThemeRepository;
import com.expexchangeservice.service.config.DictionaryServiceTestContextConfiguration;
import com.expexchangeservice.service.interfaces.IDictionaryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DictionaryServiceTestContextConfiguration.class)
public class DictionaryServiceTest {

    @Autowired
    private IDictionaryService dictionaryService;
    @Autowired
    private IThemeRepository themeRepository;
    @Autowired
    private ISectionRepository sectionRepository;

    private static Theme EXPECTED_THEME;
    private static ThemeDto EXPECTED_THEME_DTO;
    private static List<Theme> EXPECTED_THEME_LIST;
    private static Section EXPECTED_SECTION;
    private static SectionDto EXPECTED_SECTION_DTO;
    private static List<Section> EXPECTED_SECTION_LIST;
    private static List<SectionDto> EXPECTED_SECTION_DTO_LIST;
    private static final long ID = 1L;

    @BeforeAll
    public static void init() {
        EXPECTED_THEME = new Theme("Test theme");
        EXPECTED_THEME.setId(ID);
        EXPECTED_THEME_DTO = new ThemeDto();
        EXPECTED_THEME_DTO.setTitle(EXPECTED_THEME.getTitle());
        EXPECTED_THEME_LIST = Arrays.asList(EXPECTED_THEME);
        EXPECTED_SECTION = new Section("Test section");
        EXPECTED_SECTION.setId(1);
        EXPECTED_SECTION.setThemes(EXPECTED_THEME_LIST);
        EXPECTED_SECTION_DTO = new SectionDto();
        EXPECTED_SECTION_DTO.setId(EXPECTED_SECTION.getId());
        EXPECTED_SECTION_DTO.setTitle(EXPECTED_SECTION.getTitle());
        EXPECTED_SECTION_LIST = Arrays.asList(EXPECTED_SECTION);
        EXPECTED_SECTION_DTO_LIST = Arrays.asList(EXPECTED_SECTION_DTO);
    }

    @Test
    public void getThemeDictionaryWithoutErrors() {
        doReturn(EXPECTED_THEME_LIST).when(themeRepository).readAll();

        List<Theme> actualThemeList = dictionaryService.getThemeDictionary();
        assertEquals(EXPECTED_THEME_LIST, actualThemeList);
    }

    @Test
    public void getThemeDictionaryOnTheSectionWithoutErrors() {
        doReturn(EXPECTED_SECTION).when(sectionRepository).read(ID);

        List<Theme> actualThemeList = dictionaryService.getThemeDictionaryOnTheSection(ID);
        assertEquals(EXPECTED_THEME_LIST, actualThemeList);
    }

    @Test
    public void getSectionDictionaryWithoutErrors() {
        doReturn(EXPECTED_SECTION_LIST).when(sectionRepository).readAll();

        List<SectionDto> actualSectionDtoList = dictionaryService.getSectionDictionary();
        assertEquals(EXPECTED_SECTION_DTO_LIST, actualSectionDtoList);
    }

    @Test
    public void getThemeByIdWithoutErrors() {
        doReturn(EXPECTED_THEME).when(themeRepository).read(ID);

        Theme actualTheme = dictionaryService.getThemeById(ID);
        assertEquals(EXPECTED_THEME, actualTheme);
    }

    @Test
    public void getSectionByIdWithoutErrors() {
        doReturn(EXPECTED_SECTION).when(sectionRepository).read(ID);

        SectionDto actualSectionDto = dictionaryService.getSectionById(ID);
        assertEquals(EXPECTED_SECTION_DTO, actualSectionDto);
    }

    @Test
    public void updateThemeWithoutErrors() {
        Theme actualTheme = new Theme("123");
        actualTheme.setId(EXPECTED_THEME.getId());

        doReturn(actualTheme).when(themeRepository).read(ID);
        doNothing().when(themeRepository).update(any(Theme.class));

        boolean isUpdate = dictionaryService.updateTheme(ID, EXPECTED_THEME_DTO);
        assertTrue(isUpdate);
        assertEquals(EXPECTED_THEME, actualTheme);
    }

    @Test
    public void updateThemeWhenThemeNotInDatabase() {
        doReturn(null).when(themeRepository).read(ID);

        boolean isUpdate = dictionaryService.updateTheme(ID, EXPECTED_THEME_DTO);
        assertFalse(isUpdate);
    }

    @Test
    public void updateSectionWithoutErrors() {
        Section actualSection = new Section("123");
        actualSection.setId(EXPECTED_SECTION.getId());
        actualSection.setThemes(EXPECTED_THEME_LIST);

        doReturn(actualSection).when(sectionRepository).read(ID);
        doNothing().when(sectionRepository).update(any(Section.class));

        boolean isUpdate = dictionaryService.updateSection(EXPECTED_SECTION_DTO);
        assertTrue(isUpdate);
        assertEquals(EXPECTED_SECTION, actualSection);
    }

    @Test
    public void updateSectionWhenSectionDtoIsNull() {
        assertFalse(dictionaryService.updateSection(null));
    }

    @Test
    public void createThemeWithoutErrors() {
        Section actualSection = new Section(EXPECTED_SECTION.getTitle());
        actualSection.setId(EXPECTED_SECTION.getId());
        actualSection.setThemes(new ArrayList<>());

        doNothing().when(themeRepository).create(any(Theme.class));
        doReturn(actualSection).when(sectionRepository).read(ID);
        doNothing().when(sectionRepository).update(any(Section.class));

        assertTrue(dictionaryService.createTheme(ID, EXPECTED_THEME_DTO));
    }

    @Test
    public void createThemeWhenSectionNotInDatabase() {
        doReturn(null).when(sectionRepository).read(ID);

        boolean isUpdate = dictionaryService.createTheme(ID, EXPECTED_THEME_DTO);
        assertFalse(isUpdate);
    }

    @Test
    public void createSectionWithoutErrors() {
        doNothing().when(sectionRepository).create(any(Section.class));

        assertTrue(dictionaryService.createSection(EXPECTED_SECTION_DTO));
    }

    @Test
    public void createSectionWhenSectionDtoIsNull() {
        assertFalse(dictionaryService.createSection(null));
    }

    @Test
    public void deleteThemeWithoutErrors() {
        long themeId = 2L;
        Theme theme = new Theme("123");
        theme.setId(themeId);
        List<Theme> themeList = new ArrayList<>();
        themeList.add(EXPECTED_THEME);
        themeList.add(theme);
        Section actualSection = new Section(EXPECTED_SECTION.getTitle());
        actualSection.setId(ID);
        actualSection.setThemes(themeList);

        doReturn(actualSection).when(sectionRepository).read(ID);
        doReturn(theme).when(themeRepository).read(themeId);
        doNothing().when(sectionRepository).update(any(Section.class));
        doNothing().when(themeRepository).delete(theme);

        assertTrue(dictionaryService.deleteTheme(ID, themeId));
        assertEquals(EXPECTED_SECTION, actualSection);
    }

    @Test
    public void deleteThemeWhenSectionOrThemeNotInDatabase() {
        doReturn(null).when(sectionRepository).read(ID);
        doReturn(null).when(themeRepository).read(ID);

        assertFalse(dictionaryService.deleteTheme(ID, ID));
    }

    @Test
    public void deleteSectionWithoutErrors() {
        doReturn(EXPECTED_SECTION).when(sectionRepository).read(ID);
        doNothing().when(sectionRepository).delete(EXPECTED_SECTION);

        assertTrue(dictionaryService.deleteSection(ID));
    }

    @Test
    public void deleteSectionWhenSectionNotInDatabase() {
        doReturn(null).when(sectionRepository).read(ID);

        assertFalse(dictionaryService.deleteSection(ID));
    }
}
