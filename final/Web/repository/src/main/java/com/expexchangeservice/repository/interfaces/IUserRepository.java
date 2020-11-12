package com.expexchangeservice.repository.interfaces;

import com.expexchangeservice.model.entities.User;

public interface IUserRepository extends IRepository<User>  {
    User findByUsername(String username);
}
