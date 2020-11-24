package com.expexchangeservice.repository.interfaces;

import com.expexchangeservice.model.entities.Course;
import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.model.enums.Type;

import java.time.LocalDate;
import java.util.List;

public interface ICourseRepository extends IRepository<Course> {

    List<Course> findByDate(LocalDate date);
    List<Course> findAfterDate(LocalDate date);
    List<Course> findBySection(Section section);
    List<Course> findByType(Type type);
    List<Course> findByProfessor(UserProfile profile);
}
