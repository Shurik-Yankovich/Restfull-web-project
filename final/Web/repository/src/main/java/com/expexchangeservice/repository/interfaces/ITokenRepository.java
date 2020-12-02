package com.expexchangeservice.repository.interfaces;

import com.expexchangeservice.model.entities.Token;

public interface ITokenRepository extends IRepository<Token> {
    Token findByValue(String value);
}
