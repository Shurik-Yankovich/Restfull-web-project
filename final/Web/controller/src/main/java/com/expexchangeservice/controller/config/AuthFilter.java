package com.expexchangeservice.controller.config;

import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.model.entities.UserAuthentication;
import com.expexchangeservice.service.impl.UserService;
import com.expexchangeservice.utils.security.ITokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class AuthFilter extends GenericFilterBean {
    @Autowired
    private ITokenHandler tokenHandler;
    @Autowired
    private UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader(tokenHandler.AUTH_HEADER_NAME);
        UserAuthentication userAuth = null;
        if (tokenHandler.checkToken(token)) {
            User dbUser = userService.loadUserById(tokenHandler.getUserIdFromToken(token));
//            UserKeeper.setLoggedUser(dbUser);
            userAuth = new UserAuthentication(dbUser);
        }
        SecurityContextHolder.getContext().setAuthentication(userAuth);
        chain.doFilter(request, response);
    }
}
