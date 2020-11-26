package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.UserCreds;
import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.model.enums.Role;
import com.expexchangeservice.repository.interfaces.IUserRepository;
import com.expexchangeservice.service.interfaces.IUserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.expexchangeservice.model.enums.Role.USER;

@Service
@Transactional
public class UserService implements IUserService, UserDetailsService {
    //    @PersistenceContext
//    private EntityManager em;
    private IUserRepository userRepository;
    //    private IProfileRepository profileRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
//        this.profileRepository = profileRepository;
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User loadUserByUsernameAndPassword(String username, String password) {
        User userEntity = loadUserByUsername(username);
        if (userEntity != null) {
            if (bCryptPasswordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }

    @Override
    public User loadUserById(Integer userId) {
        return userRepository.read(userId);
    }

    @Override
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setRole(USER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.create(user);
        return true;
    }

    @Override
    public boolean deleteUser(Integer userId) {
        User user = userRepository.read(userId);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean changeUserPassword(UserCreds userCreds) {
        User user = loadUserByUsernameAndPassword(userCreds.getUser().getUsername(), userCreds.getUser().getPassword());
        if (user == null) {
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(userCreds.getNewPassword()));
        userRepository.update(user);
        return true;
    }

    @Override
    public boolean changeUserRole(String username, Role role) {
        User user = loadUserByUsername(username);
        if (user == null) {
            return false;
        }
        user.setRole(role);
        userRepository.update(user);
        return true;
    }
}
