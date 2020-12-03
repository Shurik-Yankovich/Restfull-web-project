package com.expexchangeservice.model.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username")
    private String username;
    @Column(name = "review")
    private String review;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review1 = (Review) o;
        return id == review1.id &&
                Objects.equals(username, review1.username) &&
                Objects.equals(review, review1.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, review);
    }
}
