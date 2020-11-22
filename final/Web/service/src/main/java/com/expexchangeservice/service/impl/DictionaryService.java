package com.expexchangeservice.service.impl;

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
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            List<Theme> themes = themeRepository.readAll();
            transaction.commit();
            return themes;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
            throw new DBException("Не удалось получить объекты класса " + Theme.class.getName() + " из базы данных!");
        }
    }

    @Override
    public List<Theme> getThemeDictionaryOnTheSection(Integer sectionId) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            Section section = sectionRepository.read(sectionId);
            transaction.commit();
            return section.getThemes();
        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
            throw new DBException("Не удалось получить объекты класса " + Theme.class.getName() + " из базы данных!");
        }
    }

    @Override
    public List<Section> getSectionDictionary() throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            List<Section> sections = sectionRepository.readAll();
            transaction.commit();
            return sections;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Section.class.getName() + " из базы данных!");
        }
    }

    @Override
    public Theme getThemeById(Integer themeId) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            Theme theme = themeRepository.read(themeId);
            transaction.commit();
            return theme;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Section.class.getName() + " из базы данных!");
        }
    }

    @Override
    public Section getSectionById(Integer sectionId) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            Section section = sectionRepository.read(sectionId);
            transaction.commit();
            return section;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Section.class.getName() + " из базы данных!");
        }
    }

    @Override
    public void changeTheme(int id, String title) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            Theme theme = themeRepository.read(id);
            theme.setTitle(title);
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
    public void deleteTheme(Integer sectionId, Integer themeId) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            Section section = sectionRepository.read(sectionId);
            Theme theme = themeRepository.read(themeId);
            section.getThemes().remove(theme);
            sectionRepository.update(section);
            themeRepository.delete(theme);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось удалить объект класса " + Theme.class.getName() + " из базы данных!");
        }
    }

    @Override
    public void deleteSection(Integer sectionId) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            Section section = sectionRepository.read(sectionId);
            sectionRepository.delete(section);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Section.class.getName() + " из базы данных!");
        }
    }
}
