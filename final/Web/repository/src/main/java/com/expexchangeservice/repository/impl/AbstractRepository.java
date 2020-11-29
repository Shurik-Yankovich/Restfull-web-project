package com.expexchangeservice.repository.impl;

import com.expexchangeservice.repository.interfaces.IRepository;
import com.expexchangeservice.utils.hibernate.HibernateSessionFactoryUtil;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractRepository<E> implements IRepository<E> {

    private final Class<E> entityClass;
//    @Autowired
//    private SessionFactory sessionFactory;

    public AbstractRepository() {
        this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public void create(E entity) {
//        Session session = HibernateSessionFactoryUtil.getSession();
//        session.save(entity);
//        session.close();
        HibernateSessionFactoryUtil.getSession().save(entity);
//        sessionFactory.getCurrentSession().save(entity);
    }

    @Override
    public void update(E entity) {
//        sessionFactory.getCurrentSession().saveOrUpdate(entity);
        HibernateSessionFactoryUtil.getSession().saveOrUpdate(entity);
    }

    @Override
    public E read(Integer primaryKey) {
//        return (E) sessionFactory.getCurrentSession().get(this.entityClass, primaryKey);
        return (E) HibernateSessionFactoryUtil.getSession().get(this.entityClass, primaryKey);
    }

    @Override
    public void delete(E entity) {
//        sessionFactory.getCurrentSession().delete(entity);
        HibernateSessionFactoryUtil.getSession().delete(entity);
    }

    @Override
    public List<E> readAll() {
        String hql = "From " + this.entityClass.getName();
//        return sessionFactory.getCurrentSession().createQuery(hql).list();
        return HibernateSessionFactoryUtil.getSession().createQuery(hql).list();
    }
}
