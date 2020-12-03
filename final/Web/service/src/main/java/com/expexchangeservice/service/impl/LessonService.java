package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.LessonDto;
import com.expexchangeservice.model.dto.ProfileDto;
import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.model.entities.Review;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.repository.interfaces.ILessonRepository;
import com.expexchangeservice.service.converter.DtoConverter;
import com.expexchangeservice.service.interfaces.ILessonService;
import com.expexchangeservice.service.interfaces.IReviewService;
import com.expexchangeservice.service.interfaces.IUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LessonService implements ILessonService {

    private ILessonRepository lessonRepository;
    private IReviewService reviewService;
    private IUserProfileService profileService;
    private DtoConverter converter;

    @Value("${reward_for_lesson:100}")
    private static int REWARD_FOR_LESSON;

    @Autowired
    public LessonService(ILessonRepository lessonRepository, IReviewService reviewService,
                         IUserProfileService profileService, DtoConverter converter) {
        this.lessonRepository = lessonRepository;
        this.reviewService = reviewService;
        this.profileService = profileService;
        this.converter = converter;
    }

    @Override
    public void addLesson(LessonDto lessonDto) {
        Lesson lesson = converter.convertDtoToLesson(new Lesson(), lessonDto);
        lesson.setReward(REWARD_FOR_LESSON);
        lessonRepository.create(lesson);
    }

    @Override
    public boolean changeLesson(int lessonId, LessonDto lessonDto) {
        Lesson lesson = converter.convertDtoToLesson(lessonRepository.read(lessonId), lessonDto);
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
        return converter.convertLessonToDto(lesson);
    }

    @Override
    public List<LessonDto> getAll() {
        List<Lesson> lessons = lessonRepository.readAll();
        return converter.convertLessonListToDtoList(lessons);
    }

    @Override
    public List<LessonDto> getLessonsOnTheDate(LocalDate date) {
        List<Lesson> lessons = lessonRepository.findByDate(date);
        return converter.convertLessonListToDtoList(lessons);
    }

    @Override
    public List<LessonDto> getLessonsAfterDate(LocalDate date) {
        List<Lesson> lessons = lessonRepository.findAfterDate(date);
        return converter.convertLessonListToDtoList(lessons);
    }

    @Override
    public List<LessonDto> getLessonsOnTheTheme(Theme theme) {
        List<Lesson> lessons = lessonRepository.findByTheme(theme);
        return converter.convertLessonListToDtoList(lessons);
    }

    @Override
    public List<LessonDto> getLessonsForTheProfessor(ProfileDto profileDto) {
        UserProfile professor = profileService.findProfileByUsername(profileDto.getUsername());
        List<Lesson> lessons = lessonRepository.findByProfessor(professor);
        return converter.convertLessonListToDtoList(lessons);
    }

    @Override
    public List<LessonDto> getLessonsByType(Type lessonType) {
        List<Lesson> lessons = lessonRepository.findByType(lessonType);
        return converter.convertLessonListToDtoList(lessons);
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

    @Override
    public int getRewardForLessonsByProfessor(String username) {
        UserProfile professor = profileService.findProfileByUsername(username);
        List<Lesson> lessons = lessonRepository.findByProfessor(professor);
        int sum = 0;
        for (Lesson lesson : lessons) {
            sum += lesson.getReward();
        }
        return sum;
    }

    @Override
    public boolean changeRewardByLessonId(int lessonId, int reward) {
        Lesson lesson = lessonRepository.read(lessonId);
        if (lesson == null) {
            return false;
        }
        lesson.setReward(reward);
        lessonRepository.update(lesson);
        return true;
    }

}
