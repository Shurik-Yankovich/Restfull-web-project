package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.dto.UserCreds;
import com.expexchangeservice.model.dto.UserDto;
import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.model.enums.Role;

public interface IUserService {

    User loadUserByUsername(String username);
    User loadUserByUsernameAndPassword(String username, String password);
    User loadUserById(Integer userId);
    boolean saveUser(User user);
    boolean deleteUser(Integer primaryKey);
    boolean changeUserPassword(UserCreds userCreds);
    boolean changeUserRole(String username, Role role);
}
