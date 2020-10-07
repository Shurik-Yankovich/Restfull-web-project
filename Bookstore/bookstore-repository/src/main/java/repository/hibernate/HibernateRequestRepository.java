package repository.hibernate;

import entity.Request;
import org.springframework.stereotype.Repository;
import repository.base.RequestRepository;

@Repository
public class HibernateRequestRepository extends AbstractHibernateRepository<Request, Integer> implements RequestRepository {
}
