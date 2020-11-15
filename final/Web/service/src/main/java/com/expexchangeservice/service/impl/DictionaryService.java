package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.repository.interfaces.ISectionRepository;
import com.expexchangeservice.repository.interfaces.IThemeRepository;
import com.expexchangeservice.service.interfaces.IDictionaryService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DictionaryService implements IDictionaryService {

    private IThemeRepository themeRepository;
    private ISectionRepository sectionRepository;
    private SessionFactory sessionFactory;

    @Autowired
    public DictionaryService(IThemeRepository themeRepository, ISectionRepository sectionRepository, SessionFactory sessionFactory) {
        this.themeRepository = themeRepository;
        this.sectionRepository = sectionRepository;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Set<Theme> getThemeDictionary() {
        return null;
    }

    @Override
    public Set<Theme> getThemeDictionaryOnTheSection(Section section) {
        return null;
    }

    @Override
    public Set<Section> getSectionDictionary() {
        return null;
    }

    @Override
    public void changeTheme(Theme theme) {

    }

    @Override
    public void changeSection(Section section) {

    }

    @Override
    public void addTheme(Theme theme) {

    }

    @Override
    public void addSection(Section section) {

    }

    @Override
    public void deleteTheme(Integer primaryKey) {

    }

    @Override
    public void deleteSection(Integer primaryKey) {

    }
}
