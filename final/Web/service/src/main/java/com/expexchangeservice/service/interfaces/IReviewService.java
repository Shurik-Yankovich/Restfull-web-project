package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.entities.Review;

public interface IReviewService {
    boolean createReview(Review review);
    boolean updateReview(Review review);
    Review readReview(Long reviewId);
    boolean deleteReview(Long reviewId);
}
