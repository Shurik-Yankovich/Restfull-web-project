package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.Token;
import com.expexchangeservice.repository.interfaces.ITokenRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepository extends AbstractRepository<Token> implements ITokenRepository {
}
