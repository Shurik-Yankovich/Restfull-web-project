package com.expexchangeservice.service.config;

import com.expexchangeservice.repository.impl.UserProfileRepository;
import com.expexchangeservice.repository.impl.UserRepository;
import com.expexchangeservice.repository.interfaces.IUserProfileRepository;
import com.expexchangeservice.repository.interfaces.IUserRepository;
import com.expexchangeservice.service.converter.DtoConverter;
import com.expexchangeservice.service.impl.UserProfileService;
import com.expexchangeservice.service.impl.UserService;
import com.expexchangeservice.service.interfaces.IUserProfileService;
import com.expexchangeservice.service.interfaces.IUserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UserProfileServiceTestContextConfiguration {

    @Bean
    public IUserProfileService profileService() {
        return new UserProfileService(profileRepository(), userService(), dtoConverter());
    }

    @Bean
    public IUserProfileRepository profileRepository() {
        return Mockito.mock(UserProfileRepository.class);
    }

    @Bean
    public DtoConverter dtoConverter() {
        return new DtoConverter();
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
}
