package com.expexchangeservice.model.entities;

import com.expexchangeservice.model.enums.Type;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Map;

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
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "CourseReview",
            joinColumns = @JoinColumn(name = "idCourse"),
            inverseJoinColumns = {
                    @JoinColumn(name = "idProfile"),
                    @JoinColumn(name = "review")})
    private Map<UserProfile,String> reviews;
}
