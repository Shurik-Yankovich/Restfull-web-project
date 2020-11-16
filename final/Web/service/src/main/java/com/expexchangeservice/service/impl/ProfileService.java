package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.repository.interfaces.IProfileRepository;
import com.expexchangeservice.repository.interfaces.IUserRepository;
import com.expexchangeservice.service.interfaces.IProfileService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.expexchangeservice.model.enums.Role.USER;

@Service
public class ProfileService implements IProfileService {

    private IUserRepository userRepository;
    private IProfileRepository profileRepository;
    private SessionFactory sessionFactory;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ProfileService(IUserRepository userRepository, IProfileRepository profileRepository,
                          SessionFactory sessionFactory, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.sessionFactory = sessionFactory;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User userEntity = loadUserByUsername(username);
        if (userEntity != null) {
            if (bCryptPasswordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }

    @Override
    public User findUserById(Integer userId) {
        return userRepository.read(userId);
    }

    @Override
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getLogin());
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
        if (userRepository.read(userId) != null) {
            userRepository.delete(userId);
            return true;
        }
        return false;
    }

    @Override
    public void editProfile() {

    }
}
