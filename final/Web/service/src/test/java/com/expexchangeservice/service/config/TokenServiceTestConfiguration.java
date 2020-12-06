package com.expexchangeservice.service.config;

import com.expexchangeservice.repository.impl.TokenRepository;
import com.expexchangeservice.repository.impl.UserRepository;
import com.expexchangeservice.repository.interfaces.ITokenRepository;
import com.expexchangeservice.repository.interfaces.IUserRepository;
import com.expexchangeservice.service.impl.TokenService;
import com.expexchangeservice.service.impl.UserService;
import com.expexchangeservice.service.interfaces.ITokenService;
import com.expexchangeservice.service.interfaces.IUserService;
import com.expexchangeservice.utils.security.ITokenHandler;
import com.expexchangeservice.utils.security.JwtTokenHandler;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class TokenServiceTestConfiguration {

    @Bean
    public ITokenService tokenService() {
        return new TokenService(tokenRepository(), tokenHandler(), userService());
    }

    @Bean
    public ITokenRepository tokenRepository() {
        return Mockito.mock(TokenRepository.class);
    }

    @Bean
    public ITokenHandler tokenHandler() {
        return Mockito.mock(JwtTokenHandler.class);
    }

    @Bean
    public IUserService userService() {
        return Mockito.mock(UserService.class);
    }

    @Bean
    public IUserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return Mockito.mock(BCryptPasswordEncoder.class);
    }

    @Bean
    public HttpServletRequest httpServletRequest() {
        return Mockito.mock(HttpServletRequest.class);
    }
}
