package repository.hibernate;

import entity.Request;
import repository.base.RequestRepository;

public class HibernateRequestRepository extends AbstractHibernateRepository<Request, Integer> implements RequestRepository {
}
