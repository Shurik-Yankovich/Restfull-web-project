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
    public boolean createReview(Review review) {
        if (review == null) {
            return false;
        }
        reviewRepository.create(review);
        return true;
    }

    @Override
    public boolean updateReview(Review review) {
        if (review == null) {
            return false;
        }
        reviewRepository.update(review);
        return true;
    }

    @Override
    public Review readReview(Long reviewId) {
        return reviewRepository.read(reviewId);
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        Review review = reviewRepository.read(reviewId);
        if (review == null) {
            return false;
        }
        reviewRepository.delete(review);
        return true;
    }
}
