package com.expexchangeservice.service.config;

import com.expexchangeservice.repository.impl.SectionRepository;
import com.expexchangeservice.repository.impl.ThemeRepository;
import com.expexchangeservice.repository.impl.UserRepository;
import com.expexchangeservice.repository.interfaces.ISectionRepository;
import com.expexchangeservice.repository.interfaces.IThemeRepository;
import com.expexchangeservice.repository.interfaces.IUserRepository;
import com.expexchangeservice.service.converter.DtoConverter;
import com.expexchangeservice.service.impl.DictionaryService;
import com.expexchangeservice.service.impl.UserService;
import com.expexchangeservice.service.interfaces.IDictionaryService;
import com.expexchangeservice.service.interfaces.IUserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DictionaryServiceTestContextConfiguration {

    @Bean
    public IDictionaryService dictionaryService() {
        return new DictionaryService(themeRepository(), sectionRepository(), dtoConverter());
    }

    @Bean
    public DtoConverter dtoConverter() {
        return new DtoConverter();
    }

    @Bean
    public IThemeRepository themeRepository() {
        return Mockito.mock(ThemeRepository.class);
    }

    @Bean
    public ISectionRepository sectionRepository() {
        return Mockito.mock(SectionRepository.class);
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
