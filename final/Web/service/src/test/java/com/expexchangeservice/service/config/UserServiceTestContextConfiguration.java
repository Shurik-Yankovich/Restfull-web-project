package com.expexchangeservice.service.config;

import com.expexchangeservice.repository.impl.UserRepository;
import com.expexchangeservice.repository.interfaces.IUserRepository;
import com.expexchangeservice.service.impl.UserService;
import com.expexchangeservice.service.interfaces.IUserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UserServiceTestContextConfiguration {

    @Bean
    public IUserService userService() {
        return new UserService(userRepository());
    }

    @Bean
    public IUserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return Mockito.mock(BCryptPasswordEncoder.class);
    }
}
