package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.entities.Review;
import com.expexchangeservice.repository.interfaces.IReviewRepository;
import com.expexchangeservice.service.interfaces.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewService implements IReviewService {

    IReviewRepository reviewRepository;

    @Autowired
    public ReviewService(IReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void addReview(Review review) {
        reviewRepository.create(review);
    }
}
