package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.dto.UserDto;
import com.expexchangeservice.model.entities.Token;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletRequest;

public interface ITokenService {

    String AUTH_HEADER_NAME = "Auth-Token";

    Authentication getAuthentication(ServletRequest servletRequest);
    Token generateToken(UserDto userDto);
}
