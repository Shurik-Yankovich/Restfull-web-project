package com.expexchangeservice.repository.impl;

import com.expexchangeservice.repository.interfaces.IRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractRepository<E> implements IRepository<E> {

    private final Class<E> entityClass;

    @Autowired
    private SessionFactory sessionFactory;

    public AbstractRepository() {
        this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public E create(E entity) {
        return (E) sessionFactory.getCurrentSession().save(entity);
    }

    @Override
    public void update(E entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    @Override
    public E read(Integer primaryKey) {
        return (E) sessionFactory.getCurrentSession().get(this.entityClass, primaryKey);
    }

    @Override
    public void delete(Integer primaryKey) {
        sessionFactory.getCurrentSession().delete(primaryKey);
    }

    @Override
    public List<E> readAll() {
        String hql = "From " + this.entityClass.getName();
        return sessionFactory.getCurrentSession().createQuery(hql).list();
    }
}
