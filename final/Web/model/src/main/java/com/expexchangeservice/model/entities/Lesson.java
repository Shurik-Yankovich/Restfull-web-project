package com.expexchangeservice.model.entities;

import com.expexchangeservice.model.enums.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_theme")
    private Theme theme;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_professor")
    private UserProfile professor;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "reward")
    private int reward;
    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "Lesson_Members",
            joinColumns = @JoinColumn(name = "id_lesson"),
            inverseJoinColumns = @JoinColumn(name = "id_member"))
    private Set<UserProfile> members;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Lesson_Review",
            joinColumns = @JoinColumn(name = "id_lesson"),
            inverseJoinColumns = @JoinColumn(name = "id_review"))
    private Set<Review> reviews;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return id == lesson.id &&
                reward == lesson.reward &&
                Objects.equals(theme, lesson.theme) &&
                Objects.equals(professor, lesson.professor) &&
                type == lesson.type &&
                Objects.equals(date, lesson.date) &&
                Objects.equals(members, lesson.members) &&
                Objects.equals(reviews, lesson.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, theme, professor, type, date, reward, members, reviews);
    }
}
