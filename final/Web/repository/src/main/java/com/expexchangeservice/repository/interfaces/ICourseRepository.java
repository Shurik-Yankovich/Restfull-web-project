package com.expexchangeservice.repository.interfaces;

import com.expexchangeservice.model.entities.Course;

import java.util.List;

public interface ICourseRepository extends IRepository<Course> {

    List<Course> findByQuery(String hqlQuery);
}
