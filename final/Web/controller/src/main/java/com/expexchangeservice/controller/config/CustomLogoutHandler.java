package com.expexchangeservice.controller.config;

import com.expexchangeservice.model.entities.UserAuthentication;
import com.expexchangeservice.service.interfaces.ITokenService;
import com.expexchangeservice.utils.security.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
public class CustomLogoutHandler implements LogoutHandler {

    @Autowired
    private ITokenService tokenService;

    public CustomLogoutHandler() {
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {
//        Authentication userName = tokenService.getAuthentication(request);
        SecurityContextHolder.getContext().getAuthentication();
        UserKeeper.deleteLoggedUser();
    }
}
