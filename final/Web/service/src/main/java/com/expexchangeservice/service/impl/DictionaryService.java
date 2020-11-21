package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.model.exception.DBException;
import com.expexchangeservice.repository.interfaces.ISectionRepository;
import com.expexchangeservice.repository.interfaces.IThemeRepository;
import com.expexchangeservice.service.interfaces.IDictionaryService;
import com.expexchangeservice.utils.HibernateSessionFactoryUtil;
import org.hibernate.Transaction;
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
    public List<Theme> getThemeDictionary() throws DBException {
//        Transaction transaction = null;
//        List<Theme> themes = null;
//        try {
//            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
//            themes = themeRepository.readAll();
//            transaction.commit();
//            return themes;
//        } catch (Exception e) {
//            transaction.rollback();
//            System.out.println(e.getMessage());
//            throw new DBException("Не удалось получить объекты класса " + Theme.class.getName() + " из базы данных!");
//        }
        return themeRepository.readAll();
    }

//    @Override
//    public List<Theme> getThemeDictionaryOnTheSection(Section section) throws DBException {
//    }

    @Override
    public List<Section> getSectionDictionary() throws DBException {
        Transaction transaction = null;
        List<Section> sections = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            sections = sectionRepository.readAll();
            transaction.commit();
            return sections;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Section.class.getName() + " из базы данных!");
        }
    }

    @Override
    public void changeTheme(Theme theme) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            themeRepository.update(theme);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Theme.class.getName() + " из базы данных!");
        }
    }

    @Override
    public void changeSection(Section section) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            sectionRepository.update(section);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Section.class.getName() + " из базы данных!");
        }
    }

    @Override
    public void addTheme(Theme theme) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            themeRepository.create(theme);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
            throw new DBException("Не удалось получить объекты класса " + Theme.class.getName() + " из базы данных!");
        }
    }

    @Override
    public void addSection(Section section) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            sectionRepository.create(section);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Section.class.getName() + " из базы данных!");
        }
    }

    @Override
    public void deleteTheme(Integer themeId) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            themeRepository.delete(themeId);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Theme.class.getName() + " из базы данных!");
        }
    }

    @Override
    public void deleteSection(Integer sectionId) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            sectionRepository.delete(sectionId);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Section.class.getName() + " из базы данных!");
        }
    }
}
