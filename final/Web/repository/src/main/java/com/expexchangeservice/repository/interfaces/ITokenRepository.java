package com.expexchangeservice.repository.interfaces;

import com.expexchangeservice.model.entities.Token;
import com.expexchangeservice.model.entities.User;

public interface ITokenRepository extends IRepository<Token> {
    Token findByValue(String value);
    void deleteByUser(User user);
}
