package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.ThemeDto;
import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.repository.interfaces.ISectionRepository;
import com.expexchangeservice.repository.interfaces.IThemeRepository;
import com.expexchangeservice.service.interfaces.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
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
    public List<Theme> getThemeDictionaryOnTheSection(Integer sectionId) {
        Section section = sectionRepository.read(sectionId);
        return section.getThemes();
    }

    @Override
    public List<Section> getSectionDictionary() {
        return sectionRepository.readAll();
    }

    @Override
    public Theme getThemeById(Integer themeId) {
        return themeRepository.read(themeId);
    }

    @Override
    public Section getSectionById(Integer sectionId) {
        return sectionRepository.read(sectionId);
    }

    @Override
    public void updateTheme(int id, ThemeDto themeDto) {
        Theme theme = themeRepository.read(id);
        theme.setTitle(themeDto.getTitle());
        themeRepository.update(theme);
    }

    @Override
    public void updateSection(Section section) {
        sectionRepository.update(section);
    }

    @Override
    public void createTheme(int sectionId, ThemeDto themeDto) {
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
    public void deleteTheme(Integer sectionId, Integer themeId) {
        Section section = sectionRepository.read(sectionId);
        Theme theme = themeRepository.read(themeId);
        section.getThemes().remove(theme);
        sectionRepository.update(section);
        themeRepository.delete(theme);
    }

    @Override
    public void deleteSection(Integer sectionId) {
        Section section = sectionRepository.read(sectionId);
        sectionRepository.delete(section);
    }
}
