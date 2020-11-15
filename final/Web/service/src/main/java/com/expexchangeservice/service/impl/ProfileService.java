package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.repository.interfaces.IProfileRepository;
import com.expexchangeservice.repository.interfaces.IUserRepository;
import com.expexchangeservice.service.interfaces.IProfileService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService implements IProfileService {

    private IUserRepository userRepository;
    private IProfileRepository profileRepository;
    private SessionFactory sessionFactory;

    @Autowired
    public ProfileService(IUserRepository userRepository, IProfileRepository profileRepository, SessionFactory sessionFactory) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User loadUserByUsername(String username) {
        return null;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return null;
    }

    @Override
    public User findUserById(Integer userId) {
        return null;
    }

    @Override
    public boolean saveUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(Integer primaryKey) {
        return false;
    }

    @Override
    public void editProfile() {

    }
}
