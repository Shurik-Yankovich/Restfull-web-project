package com.expexchangeservice.service.config;

import com.expexchangeservice.repository.impl.LessonRepository;
import com.expexchangeservice.repository.impl.UserProfileRepository;
import com.expexchangeservice.repository.impl.UserRepository;
import com.expexchangeservice.repository.interfaces.ILessonRepository;
import com.expexchangeservice.repository.interfaces.IUserProfileRepository;
import com.expexchangeservice.repository.interfaces.IUserRepository;
import com.expexchangeservice.service.converter.DtoConverter;
import com.expexchangeservice.service.impl.LessonService;
import com.expexchangeservice.service.impl.ReviewService;
import com.expexchangeservice.service.impl.UserProfileService;
import com.expexchangeservice.service.impl.UserService;
import com.expexchangeservice.service.interfaces.ILessonService;
import com.expexchangeservice.service.interfaces.IReviewService;
import com.expexchangeservice.service.interfaces.IUserProfileService;
import com.expexchangeservice.service.interfaces.IUserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class LessonServiceTestContextConfiguration {

    @Bean
    public ILessonService lessonService() {
        return new LessonService(lessonRepository(), reviewService(), profileService(), dtoConverter());
    }

    @Bean
    public ILessonRepository lessonRepository() {
        return Mockito.mock(LessonRepository.class);
    }

    @Bean
    public IReviewService reviewService() {
        return Mockito.mock(ReviewService.class);
    }

    @Bean
    public IUserProfileService profileService() {
        return Mockito.mock(UserProfileService.class);
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
