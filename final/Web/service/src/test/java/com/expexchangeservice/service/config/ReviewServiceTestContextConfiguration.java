package com.expexchangeservice.service.config;

import com.expexchangeservice.repository.impl.ReviewRepository;
import com.expexchangeservice.repository.interfaces.IReviewRepository;
import com.expexchangeservice.service.impl.ReviewService;
import com.expexchangeservice.service.interfaces.IReviewService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReviewServiceTestContextConfiguration {

    @Bean
    public IReviewService reviewService(){
        return new ReviewService(reviewRepository());
    }

    @Bean
    public IReviewRepository reviewRepository(){
        return Mockito.mock(ReviewRepository.class);
    }
}
