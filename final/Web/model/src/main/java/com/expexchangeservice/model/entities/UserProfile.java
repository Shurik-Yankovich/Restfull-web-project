package com.expexchangeservice.model.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.REMOVE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "id_user")
    private User user;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "workplace")
    private String placeOfWork;
    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "Course_Members",
            joinColumns = @JoinColumn(name = "id_member"),
            inverseJoinColumns = @JoinColumn(name = "id_course"))
    private List<Course> courses;
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "Lesson_Members",
            joinColumns = @JoinColumn(name = "id_member"),
            inverseJoinColumns = @JoinColumn(name = "id_lesson"))
    private List<Lesson> lessons;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPlaceOfWork() {
        return placeOfWork;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile profile = (UserProfile) o;
        return id == profile.id &&
                Objects.equals(user, profile.user) &&
                Objects.equals(fullName, profile.fullName) &&
                Objects.equals(placeOfWork, profile.placeOfWork);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, fullName, placeOfWork);
    }
}
