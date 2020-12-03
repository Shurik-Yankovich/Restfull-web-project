package com.expexchangeservice.controller.config;

import com.expexchangeservice.service.interfaces.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
public class AuthFilter extends GenericFilterBean {
    @Autowired
    private ITokenService tokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(tokenService.getAuthentication(request));
        chain.doFilter(request, response);
    }
}
