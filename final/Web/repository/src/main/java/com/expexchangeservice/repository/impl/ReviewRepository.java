package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.Review;
import com.expexchangeservice.repository.interfaces.IReviewRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepository extends AbstractRepository<Review> implements IReviewRepository {
}
