package com.expexchangeservice.model.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "idTheme")
    private Theme theme;
    @OneToOne(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "idProfessor")
    private UserProfile professor;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "price")
    private int price;
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "LessonMembers",
            joinColumns = @JoinColumn(name = "idLesson"),
            inverseJoinColumns = @JoinColumn(name = "idMember"))
    private Set<UserProfile> members;
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "LessonReview",
            joinColumns = @JoinColumn(name = "idLesson"),
            inverseJoinColumns = @JoinColumn(name = "idReview"))
    private Set<Review> reviews;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public UserProfile getProfessor() {
        return professor;
    }

    public void setProfessor(UserProfile professor) {
        this.professor = professor;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Set<UserProfile> getMembers() {
        return members;
    }

    public void setMembers(Set<UserProfile> members) {
        this.members = members;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
}
