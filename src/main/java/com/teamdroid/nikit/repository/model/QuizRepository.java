package com.teamdroid.nikit.repository.model;

import com.teamdroid.nikit.entity.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends MongoRepository<Quiz, String> {
    List<Quiz> findByIdIn(List<String> quizIds);
    List<Quiz> findByTopicIdsContaining(String topicId);
    List<Quiz> findByTopicIdsIn(List<String> topicIds);
}
