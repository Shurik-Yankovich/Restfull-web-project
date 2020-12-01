package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.LessonDto;
import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.model.entities.Review;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.repository.interfaces.ILessonRepository;
import com.expexchangeservice.service.converter.DtoConverter;
import com.expexchangeservice.service.interfaces.ILessonService;
import com.expexchangeservice.service.interfaces.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class LessonService implements ILessonService {

    private ILessonRepository lessonRepository;
    private IReviewService reviewService;
//    private IProfileService profileService;

    @Autowired
    public LessonService(ILessonRepository lessonRepository, IReviewService reviewService) {
        this.lessonRepository = lessonRepository;
        this.reviewService = reviewService;
//        this.profileService = profileService;
    }

    @Override
    public void addLesson(LessonDto lessonDto) {
        Lesson lesson = DtoConverter.convertDtoToLesson(new Lesson(), lessonDto);
        lesson.setPrice(100);
        lessonRepository.create(lesson);
    }

    @Override
    public boolean changeLesson(int lessonId, LessonDto lessonDto) {
        Lesson lesson = DtoConverter.convertDtoToLesson(lessonRepository.read(lessonId), lessonDto);
        if (lesson == null) {
            return false;
        }
        lessonRepository.update(lesson);
        return true;
    }

    @Override
    public boolean deleteLesson(Integer lessonId) {
        Lesson lesson = lessonRepository.read(lessonId);
        if (lesson == null) {
            return false;
        }
        lessonRepository.delete(lesson);
        return true;
    }

    @Override
    public LessonDto getLessonById(Integer lessonId) {
        Lesson lesson = lessonRepository.read(lessonId);
        return DtoConverter.convertLessonToDto(lesson);
    }

    @Override
    public List<LessonDto> getAll() {
        List<Lesson> lessons = lessonRepository.readAll();
        return DtoConverter.convertLessonListToDtoList(lessons);
    }

    @Override
    public List<LessonDto> getLessonsOnTheDate(LocalDate date) {
        List<Lesson> lessons = lessonRepository.findByDate(date);
        return DtoConverter.convertLessonListToDtoList(lessons);
    }

    @Override
    public List<LessonDto> getLessonsAfterDate(LocalDate date) {
        List<Lesson> lessons = lessonRepository.findAfterDate(date);
        return DtoConverter.convertLessonListToDtoList(lessons);
    }

    @Override
    public List<LessonDto> getLessonsOnTheTheme(Theme theme) {
        List<Lesson> lessons = lessonRepository.findByTheme(theme);
        return DtoConverter.convertLessonListToDtoList(lessons);
    }

    @Override
    public List<LessonDto> getLessonsForTheProfessor(UserProfile professor) {
        List<Lesson> lessons = lessonRepository.findByProfessor(professor);
        return DtoConverter.convertLessonListToDtoList(lessons);
    }

    @Override
    public List<LessonDto> getLessonsByType(Type lessonType) {
        List<Lesson> lessons = lessonRepository.findByType(lessonType);
        return DtoConverter.convertLessonListToDtoList(lessons);
    }

    @Override
    public boolean addReview(Integer lessonId, Review review) {
        reviewService.addReview(review);
        Lesson lesson = lessonRepository.read(lessonId);
        if (lesson == null) {
            return false;
        }
        if (lesson.getReviews() == null) {
            lesson.setReviews(new HashSet<>());
        }
        lesson.getReviews().add(review);
        lessonRepository.update(lesson);
        return true;
    }

    @Override
    public Set<Review> getReviewOnTheLesson(Integer lessonId) {
        Lesson lesson = lessonRepository.read(lessonId);
        return lesson.getReviews();
    }

}
