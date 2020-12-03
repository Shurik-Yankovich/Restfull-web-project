package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.entities.Review;

public interface IReviewService {
    void addReview(Review review);
    void updateReview(Review review);
    Review readReview(Long reviewId);
    void deleteReview(Long reviewId);
}
