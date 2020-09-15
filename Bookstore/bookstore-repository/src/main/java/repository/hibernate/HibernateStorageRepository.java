package repository.hibernate;

import entity.Bookshelf;
import repository.base.StorageRepository;

public class HibernateStorageRepository extends AbstractHibernateRepository<Bookshelf, Integer> implements StorageRepository {
}
