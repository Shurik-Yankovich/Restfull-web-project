package com.expexchangeservice.repository.impl;

import com.expexchangeservice.repository.interfaces.IRepository;
import com.expexchangeservice.utils.hibernate.HibernateSessionFactoryUtil;

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
        HibernateSessionFactoryUtil.getSession().saveOrUpdate(entity);
    }

    @Override
    public E read(Long primaryKey) {
        return (E) HibernateSessionFactoryUtil.getSession().get(this.entityClass, primaryKey);
    }

    @Override
    public void delete(E entity) {
        HibernateSessionFactoryUtil.getSession().delete(entity);
    }

    @Override
    public List<E> readAll() {
        String hql = "From " + this.entityClass.getName();
        return HibernateSessionFactoryUtil.getSession().createQuery(hql).list();
    }
}
