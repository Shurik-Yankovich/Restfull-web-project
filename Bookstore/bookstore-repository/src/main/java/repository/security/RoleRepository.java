package repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import entity.security.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
//public class RoleRepository extends AbstractHibernateRepository<Role, Long>{
//}
