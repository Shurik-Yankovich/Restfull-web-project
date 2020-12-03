package com.expexchangeservice.model.entities;

import com.expexchangeservice.model.enums.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private long id;
    @OneToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "id_section")
    private Section section;
    @OneToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "id_professor")
    private UserProfile professor;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @Column(name = "date_start")
    private LocalDate dateStart;
    @Column(name = "count_lesson")
    private int countLesson;
    @Column(name = "reward")
    private int reward;
    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "Course_Members",
            joinColumns = @JoinColumn(name = "id_course"),
            inverseJoinColumns = @JoinColumn(name = "id_member"))
    private Set<UserProfile> members;
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "Course_Review",
            joinColumns = @JoinColumn(name = "id_course"),
            inverseJoinColumns = @JoinColumn(name = "id_review"))
    private Set<Review> reviews;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
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
