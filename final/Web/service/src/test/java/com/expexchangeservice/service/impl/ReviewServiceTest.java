package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.entities.Review;
import com.expexchangeservice.repository.interfaces.IReviewRepository;
import com.expexchangeservice.service.config.ReviewServiceTestContextConfiguration;
import com.expexchangeservice.service.interfaces.IReviewService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ReviewServiceTestContextConfiguration.class)
public class ReviewServiceTest {

    @Autowired
    private IReviewService reviewService;
    @Autowired
    private IReviewRepository reviewRepository;

    private static Review EXPECTED_REVIEW;
    private static final long ID = 1L;

    @BeforeAll
    public static void init() {
        EXPECTED_REVIEW = new Review();
        EXPECTED_REVIEW.setId(ID);
        EXPECTED_REVIEW.setUsername("user");
        EXPECTED_REVIEW.setReview("Review");
    }

    @Test
    public void createReviewWithoutErrors() {
        doNothing().when(reviewRepository).create(EXPECTED_REVIEW);

        assertTrue(reviewService.createReview(EXPECTED_REVIEW));
    }

    @Test
    public void createReviewWhenReviewIsNull() {
        assertFalse(reviewService.createReview(null));
    }

    @Test
    public void updateReviewWithoutErrors() {
        doNothing().when(reviewRepository).update(EXPECTED_REVIEW);

        assertTrue(reviewService.updateReview(EXPECTED_REVIEW));
    }

    @Test
    public void updateReviewWhenReviewIsNull() {
        assertFalse(reviewService.updateReview(null));
    }

    @Test
    public void readReviewWithoutErrors() {
        doReturn(EXPECTED_REVIEW).when(reviewRepository).read(ID);

        Review actualReview = reviewService.readReview(ID);
        assertEquals(EXPECTED_REVIEW, actualReview);
    }

    @Test
    public void deleteReviewWithoutErrors() {
        doReturn(EXPECTED_REVIEW).when(reviewRepository).read(ID);
        doNothing().when(reviewRepository).delete(any(Review.class));

        assertTrue(reviewService.deleteReview(ID));
    }

    @Test
    public void deleteReviewWhenReviewNotInDatabase() {
        doReturn(null).when(reviewRepository).read(ID);

        assertFalse(reviewService.deleteReview(ID));
    }
}
