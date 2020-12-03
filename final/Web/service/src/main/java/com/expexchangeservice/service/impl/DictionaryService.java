package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.ThemeDto;
import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.repository.interfaces.ISectionRepository;
import com.expexchangeservice.repository.interfaces.IThemeRepository;
import com.expexchangeservice.service.interfaces.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryService implements IDictionaryService {

    private IThemeRepository themeRepository;
    private ISectionRepository sectionRepository;

    @Autowired
    public DictionaryService(IThemeRepository themeRepository, ISectionRepository sectionRepository) {
        this.themeRepository = themeRepository;
        this.sectionRepository = sectionRepository;
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
    public List<Section> getSectionDictionary() {
        return sectionRepository.readAll();
    }

    @Override
    public Theme getThemeById(Long themeId) {
        return themeRepository.read(themeId);
    }

    @Override
    public Section getSectionById(Long sectionId) {
        return sectionRepository.read(sectionId);
    }

    @Override
    public boolean updateTheme(Long themeId, ThemeDto themeDto) {
        Theme theme = themeRepository.read(themeId);
        if (theme == null) {
            return false;
        }
        theme.setTitle(themeDto.getTitle());
        themeRepository.update(theme);
        return true;
    }

    //TODO: просмотреть обновление секции, может какие изменения внести нужно!
    @Override
    public boolean updateSection(Section section) {
        if (section == null) {
            return false;
        }
        sectionRepository.update(section);
        return true;
    }

    @Override
    public void createTheme(Long sectionId, ThemeDto themeDto) {
        Theme theme = new Theme(themeDto.getTitle());
        themeRepository.create(theme);
        Section section = sectionRepository.read(sectionId);
        section.getThemes().add(theme);
        sectionRepository.update(section);
    }

    @Override
    public void createSection(Section section) {
        sectionRepository.create(section);
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
