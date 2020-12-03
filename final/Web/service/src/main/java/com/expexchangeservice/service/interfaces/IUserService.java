package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.dto.UserCreds;
import com.expexchangeservice.model.dto.UserDto;
import com.expexchangeservice.model.entities.User;

public interface IUserService {

    User loadUserByUsername(String username);
    User loadUserByUsernameAndPassword(String username, String password);
    User loadUserById(Long userId);
    boolean saveUser(UserDto userDto);
    boolean deleteUser(Long primaryKey);
    boolean changeUserPassword(UserCreds userCreds);
    User changeUserRole(String username, boolean isAdmin);
}
