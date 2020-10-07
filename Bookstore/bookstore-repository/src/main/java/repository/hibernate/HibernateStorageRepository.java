package repository.hibernate;

import entity.Bookshelf;
import org.springframework.stereotype.Repository;
import repository.base.StorageRepository;

@Repository
public class HibernateStorageRepository extends AbstractHibernateRepository<Bookshelf, Integer> implements StorageRepository {
}
