package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.UserDto;
import com.expexchangeservice.model.entities.Token;
import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.model.entities.UserAuthentication;
import com.expexchangeservice.repository.interfaces.ITokenRepository;
import com.expexchangeservice.service.interfaces.ITokenService;
import com.expexchangeservice.utils.security.ITokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@Service
public class TokenService implements ITokenService {

//    private static final String AUTH_HEADER_NAME = "Auth-Token";

    private final ITokenRepository tokenRepository;
    private final ITokenHandler tokenHandler;
    private final UserService userService;

    @Autowired
    public TokenService(ITokenRepository tokenRepository, ITokenHandler tokenHandler, UserService userService) {
        this.tokenRepository = tokenRepository;
        this.tokenHandler = tokenHandler;
        this.userService = userService;
    }

    @Override
    public Authentication getAuthentication(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader(AUTH_HEADER_NAME);
        if (tokenHandler.checkToken(token)) {
            User user = userService.loadUserById(tokenHandler.getUserIdFromToken(token));
            return new UserAuthentication(user);
        }
        return null;
    }

    @Override
    public Token generateToken(UserDto userDto) {
        User user = userService.loadUserByUsername(userDto.getUsername());
        if (user != null) {
            Token token = createToken(user);
            tokenRepository.create(token);
            return token;
        }
        return null;
    }

    private Token createToken(User user) {
        return new Token(tokenHandler.generateToken(user.getId()), user);
    }
}
