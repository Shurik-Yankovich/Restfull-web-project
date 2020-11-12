package com.expexchangeservice.model.entities;

import com.expexchangeservice.model.enums.Type;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "idSection")
    private Section section;
    @OneToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "idProfessor")
    private UserProfile professor;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;
    @Column(name = "dateStart")
    private LocalDate dateStart;
    @Column(name = "countLesson")
    private int countLesson;
    @Column(name = "price")
    private int price;
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "CourseReview",
            joinColumns = @JoinColumn(name = "idCourse"),
            inverseJoinColumns = @JoinColumn(name = "idReview"))
    private Set<Review> reviews;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
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

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public int getCountLesson() {
        return countLesson;
    }

    public void setCountLesson(int countLesson) {
        this.countLesson = countLesson;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
}
