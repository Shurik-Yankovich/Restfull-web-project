package com.expexchangeservice.repository.interfaces;

import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.model.enums.Type;

import java.time.LocalDate;
import java.util.List;

public interface ILessonRepository extends IRepository<Lesson>  {

    List<Lesson> findByQuery(String hqlQuery);
    List<Lesson> findByDate(LocalDate date);
    List<Lesson> findAfterDate(LocalDate date);
    List<Lesson> findByTheme(Theme theme);
    List<Lesson> findByType(Type type);
    List<Lesson> findByUserProfile(UserProfile profile);
}
