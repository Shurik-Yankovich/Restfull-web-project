package com.expexchangeservice.service.config;

import com.expexchangeservice.repository.impl.CourseRepository;
import com.expexchangeservice.repository.impl.UserRepository;
import com.expexchangeservice.repository.interfaces.ICourseRepository;
import com.expexchangeservice.repository.interfaces.IUserRepository;
import com.expexchangeservice.service.converter.DtoConverter;
import com.expexchangeservice.service.impl.*;
import com.expexchangeservice.service.interfaces.*;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class CourseServiceTestContextConfiguration {

    @Bean
    public ICourseService courseService() {
        return new CourseService(courseRepository(), reviewService(), profileService(), dtoConverter());
    }

    @Bean
    public ICourseRepository courseRepository() {
        return Mockito.mock(CourseRepository.class);
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
