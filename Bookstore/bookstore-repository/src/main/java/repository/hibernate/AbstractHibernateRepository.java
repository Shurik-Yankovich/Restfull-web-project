package repository.hibernate;

import exeption.RepositoryException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.lang.reflect.ParameterizedType;
import repository.base.Repository;
import util.hibernate.HibernateSessionFactoryUtil;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractHibernateRepository<E, PK extends Serializable> implements Repository<E, PK> {

    private final Class<E> entityClass;

    public AbstractHibernateRepository() {
        this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public E create(E entity) throws RepositoryException {
        Session session = HibernateSessionFactoryUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            entity = null;
            throw new RepositoryException("Не удалось записать объект класса " + this.entityClass.getName() + " в базу данных!");
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public E update(E entity) throws RepositoryException {
        Session session = HibernateSessionFactoryUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            entity = null;
            throw new RepositoryException("Не удалось обновить объект класса " + this.entityClass.getName() + " в базе данных!");
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public E read(PK primaryKey) throws RepositoryException {
        Session session = HibernateSessionFactoryUtil.getSession();
        Transaction transaction = null;
        E entity;
        try {
            transaction = session.beginTransaction();
            entity = session.get(this.entityClass, primaryKey);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            entity = null;
            throw new RepositoryException("Не удалось прочитать объект класса " + this.entityClass.getName() + " в базе данных!");
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public void delete(PK primaryKey) throws RepositoryException {
        Session session = HibernateSessionFactoryUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            E entity = session.get(this.entityClass, primaryKey);
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RepositoryException("Не удалось прочитать объект класса " + this.entityClass.getName() + " в базе данных!");
        } finally {
            session.close();
        }
    }

    @Override
    public List<E> readAll() throws RepositoryException {
        Session session = HibernateSessionFactoryUtil.getSession();
        Transaction transaction = null;
        List<E> entityList;
        String hql = "From " + this.entityClass.getName();
        try {
            transaction = session.beginTransaction();
            entityList = session.createQuery(hql).list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            entityList = null;
            throw new RepositoryException("Не удалось прочитать список объектов класса " + this.entityClass.getName() + " в базе данных!");
        } finally {
            session.close();
        }
        return entityList;
    }


    @Override
    public void createAll(List<E> list) throws RepositoryException {
        Session session = HibernateSessionFactoryUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            for (E entity : list) {
                session.save(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RepositoryException("Не удалось записать объекты класса " + this.entityClass.getName() + " в базу данных!");
        } finally {
            session.close();
        }
    }
}
