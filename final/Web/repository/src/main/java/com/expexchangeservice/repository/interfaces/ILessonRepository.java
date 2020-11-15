package com.expexchangeservice.repository.interfaces;

import com.expexchangeservice.model.entities.Lesson;

import java.util.List;

public interface ILessonRepository extends IRepository<Lesson>  {

    List<Lesson> findByQuery(String hqlQuery);
}
