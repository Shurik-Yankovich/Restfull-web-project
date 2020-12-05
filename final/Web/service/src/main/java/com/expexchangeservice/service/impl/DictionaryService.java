package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.SectionDto;
import com.expexchangeservice.model.dto.ThemeDto;
import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.repository.interfaces.ISectionRepository;
import com.expexchangeservice.repository.interfaces.IThemeRepository;
import com.expexchangeservice.service.converter.DtoConverter;
import com.expexchangeservice.service.interfaces.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryService implements IDictionaryService {

    private IThemeRepository themeRepository;
    private ISectionRepository sectionRepository;
    private DtoConverter converter;

    @Autowired
    public DictionaryService(IThemeRepository themeRepository, ISectionRepository sectionRepository,
                             DtoConverter converter) {
        this.themeRepository = themeRepository;
        this.sectionRepository = sectionRepository;
        this.converter = converter;
    }

    @Override
    public List<Theme> getThemeDictionary() {
        return themeRepository.readAll();
    }

    @Override
    public List<Theme> getThemeDictionaryOnTheSection(Long sectionId) {
        Section section = sectionRepository.read(sectionId);
        return section.getThemes();
    }

    @Override
    public List<SectionDto> getSectionDictionary() {
        return converter.convertSectionListToDtoList(sectionRepository.readAll());
    }

    @Override
    public Theme getThemeById(Long themeId) {
        return themeRepository.read(themeId);
    }

    @Override
    public SectionDto getSectionById(Long sectionId) {
        return converter.convertSectionToDto(sectionRepository.read(sectionId));
    }

    @Override
    public boolean updateTheme(Long themeId, ThemeDto themeDto) {
        Theme theme = themeRepository.read(themeId);
        if (theme == null || themeDto == null) {
            return false;
        }
        theme.setTitle(themeDto.getTitle());
        themeRepository.update(theme);
        return true;
    }

    @Override
    public boolean updateSection(SectionDto sectionDto) {
        if (sectionDto == null) {
            return false;
        }
        Section section = converter.convertDtoToSection(sectionRepository.read(sectionDto.getId()), sectionDto);
        sectionRepository.update(section);
        return true;
    }

    @Override
    public boolean createTheme(Long sectionId, ThemeDto themeDto) {
        Section section = sectionRepository.read(sectionId);
        if (section == null || themeDto == null) {
            return false;
        }
        Theme theme = new Theme(themeDto.getTitle());
        themeRepository.create(theme);
        section.getThemes().add(theme);
        sectionRepository.update(section);
        return true;
    }

    @Override
    public boolean createSection(SectionDto sectionDto) {
        if (sectionDto == null) {
            return false;
        }
        Section section = converter.convertDtoToSection(new Section(), sectionDto);
        sectionRepository.create(section);
        return true;
    }

    @Override
    public boolean deleteTheme(Long sectionId, Long themeId) {
        Section section = sectionRepository.read(sectionId);
        Theme theme = themeRepository.read(themeId);
        if (section == null || theme == null) {
            return false;
        }
        section.getThemes().remove(theme);
        sectionRepository.update(section);
        themeRepository.delete(theme);
        return true;
    }

    @Override
    public boolean deleteSection(Long sectionId) {
        Section section = sectionRepository.read(sectionId);
        if (section == null) {
            return false;
        }
        sectionRepository.delete(section);
        return true;
    }
}
