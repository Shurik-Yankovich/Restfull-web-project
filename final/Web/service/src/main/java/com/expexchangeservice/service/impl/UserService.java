package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.UserCreds;
import com.expexchangeservice.model.dto.UserDto;
import com.expexchangeservice.model.enums.Role;
import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.repository.interfaces.IUserRepository;
import com.expexchangeservice.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserService implements IUserService, UserDetailsService {
//    @PersistenceContext
//    private EntityManager em;
    private IUserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
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
    public User loadUserById(Long userId) {
        return userRepository.read(userId);
    }

    @Override
    public boolean createUser(UserDto userDto) {
        User userFromDB = userRepository.findByUsername(userDto.getUsername());
        if (userFromDB != null) {
            return false;
        }
        User user = convertDtoToUser(userDto);
        user.setRole(Role.ROLE_USER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.create(user);
        return true;
    }

    @Override
    public boolean deleteUser(Long userId) {
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
    public boolean changeUserRole(String username, boolean isAdmin) {
        User user = loadUserByUsername(username);
        if (user == null) {
            return false;
        }
        if (isAdmin) {
            user.setRole(Role.ROLE_ADMIN);
        } else {
            user.setRole(Role.ROLE_USER);
        }
        userRepository.update(user);
        return true;
    }

    private User convertDtoToUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }

}
