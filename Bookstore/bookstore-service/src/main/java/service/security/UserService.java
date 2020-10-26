package service.security;

import exeption.RepositoryException;
import entity.security.Role;
import entity.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import repository.security.RoleRepository;
import repository.security.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User loadUserByUsername(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findUserById(Integer userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
//        User userFromDb = userRepository.read(userId);
//        return userFromDb;
    }

    public List<User> allUsers() {
        return userRepository.findAll();
//        return userRepository.readAll();
    }

    public boolean saveUser(User user) {
//        try {
            User userFromDB = userRepository.findByUsername(user.getUsername());

            if (userFromDB != null) {
                return false;
            }

            user.setRoles(Collections.singleton(new Role(2, "USER")));
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

//            userRepository.create(user);
//        } catch (RepositoryException e) {
//            return false;
//        }
        return true;
    }

    public boolean deleteUser(Integer userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
//        if (userRepository.read(userId).isPresent()) {
//            userRepository.delete(userId);
//            return true;
//        }
        return false;
    }

    public List<User> getUserList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
}
