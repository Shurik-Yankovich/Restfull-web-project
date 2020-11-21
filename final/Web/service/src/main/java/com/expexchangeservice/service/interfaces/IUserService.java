package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.entities.User;

public interface IUserService {

    User loadUserByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
    User findUserById(Integer userId);
    boolean saveUser(User user);
    boolean deleteUser(Integer primaryKey);
    void editProfile(User user);
}
