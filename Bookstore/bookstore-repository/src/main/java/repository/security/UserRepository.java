package repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import entity.security.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
//public class UserRepository extends AbstractHibernateRepository<User, Long> {
//
//    public User findByUsername(String username) throws UsernameNotFoundException {
//        Session session = HibernateSessionFactoryUtil.getSession();
//        Transaction transaction = null;
//        User entity;
//        try {
//            transaction = session.beginTransaction();
//            Query query = session.createQuery("select u from " + User.class.getName() + " u where u.username = " + username)
////            .setParameter("param", username)
//            .setMaxResults(1);
//            entity = (User) query.uniqueResult();
//            transaction.commit();
//        } catch (Exception e) {
//            transaction.rollback();
//            entity = null;
//            throw new UsernameNotFoundException("Не удалось прочитать объект класса " + User.class.getName() + " в базе данных!");
//        } finally {
//            session.close();
//        }
//        return entity;
//    }
//}