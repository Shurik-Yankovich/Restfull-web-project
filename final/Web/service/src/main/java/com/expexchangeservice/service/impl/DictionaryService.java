package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.model.exception.DBException;
import com.expexchangeservice.repository.interfaces.ISectionRepository;
import com.expexchangeservice.repository.interfaces.IThemeRepository;
import com.expexchangeservice.service.interfaces.IDictionaryService;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Theme> getThemeDictionary() throws DBException {
        Transaction transaction = null;
        List<Theme> themes = null;
        try {
            transaction = sessionFactory.getCurrentSession().beginTransaction();
            themes = themeRepository.readAll();
            transaction.commit();
            return themes;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

//    @Override
//    public List<Theme> getThemeDictionaryOnTheSection(Section section) throws DBException {
//    }

    @Override
    public List<Section> getSectionDictionary() throws DBException {
        Transaction transaction = null;
        List<Section> sections = null;
        try {
            transaction = sessionFactory.getCurrentSession().beginTransaction();
            sections = sectionRepository.readAll();
            transaction.commit();
            return sections;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

    @Override
    public void changeTheme(Theme theme) throws DBException {
        Transaction transaction = null;
        try {
            transaction = sessionFactory.getCurrentSession().beginTransaction();
            themeRepository.update(theme);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

    @Override
    public void changeSection(Section section) throws DBException {
        Transaction transaction = null;
        try {
            transaction = sessionFactory.getCurrentSession().beginTransaction();
            sectionRepository.update(section);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

    @Override
    public Theme addTheme(Theme theme) throws DBException {
        Transaction transaction = null;
        try {
            transaction = sessionFactory.getCurrentSession().beginTransaction();
            theme = themeRepository.create(theme);
            transaction.commit();
            return theme;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

    @Override
    public Section addSection(Section section) throws DBException {
        Transaction transaction = null;
        try {
            transaction = sessionFactory.getCurrentSession().beginTransaction();
            section = sectionRepository.create(section);
            transaction.commit();
            return section;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

    @Override
    public void deleteTheme(Integer themeId) throws DBException {
        Transaction transaction = null;
        try {
            transaction = sessionFactory.getCurrentSession().beginTransaction();
            themeRepository.delete(themeId);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

    @Override
    public void deleteSection(Integer sectionId) throws DBException {
        Transaction transaction = null;
        try {
            transaction = sessionFactory.getCurrentSession().beginTransaction();
            sectionRepository.delete(sectionId);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Lesson.class.getName() + " из базы данных!");
        }
    }
}
