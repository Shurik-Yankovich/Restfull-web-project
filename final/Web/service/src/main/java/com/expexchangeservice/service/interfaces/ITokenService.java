package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.dto.UserDto;
import com.expexchangeservice.model.entities.Token;
import com.expexchangeservice.model.entities.User;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public interface ITokenService {

    String AUTH_HEADER_NAME = "Auth-Token";

    Authentication getAuthentication(ServletRequest servletRequest);
    Token generateToken(UserDto userDto);
    boolean checkUser(HttpServletRequest request, String username);
    boolean checkUser(HttpServletRequest request, int userId);
    boolean deleteToken(HttpServletRequest request);
    String getUsernameByRequest(HttpServletRequest request);
}
