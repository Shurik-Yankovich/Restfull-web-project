package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.model.exception.DBException;
import com.expexchangeservice.repository.interfaces.IRepository;
import com.expexchangeservice.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractRepository<E> implements IRepository<E> {

    private final Class<E> entityClass;

    public AbstractRepository() {
        this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public void create(E entity) {
        HibernateSessionFactoryUtil.getSession().save(entity);
    }

    @Override
    public void update(E entity) {
        HibernateSessionFactoryUtil.getSession().update(entity);
    }

    @Override
    public E read(Integer primaryKey) {
        return (E) HibernateSessionFactoryUtil.getSession().get(this.entityClass, primaryKey);
    }

    @Override
    public void delete(Integer primaryKey) {
        HibernateSessionFactoryUtil.getSession().delete(primaryKey);
    }

    @Override
    public List<E> readAll() throws DBException {
        Session session = HibernateSessionFactoryUtil.getSession();
        Transaction transaction = null;
        List<E> entities = null;
        try {
            transaction = session.beginTransaction();
            String hql = "From " + this.entityClass.getName();
            entities = session.createQuery(hql).list();
            transaction.commit();
            return entities;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
            throw new DBException("Не удалось получить объекты класса " + Theme.class.getName() + " из базы данных!");
        }

    }
}
