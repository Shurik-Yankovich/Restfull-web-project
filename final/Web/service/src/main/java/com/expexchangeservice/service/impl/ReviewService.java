package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.entities.Review;
import com.expexchangeservice.repository.interfaces.IReviewRepository;
import com.expexchangeservice.service.interfaces.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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

    @Override
    public void updateReview(Review review) {
        reviewRepository.update(review);
    }

    @Override
    public Review readReview(Long reviewId) {
        return reviewRepository.read(reviewId);
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.read(reviewId);
        reviewRepository.delete(review);
    }
}
