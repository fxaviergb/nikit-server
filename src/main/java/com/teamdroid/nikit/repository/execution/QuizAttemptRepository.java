package com.teamdroid.nikit.repository.execution;

import com.teamdroid.nikit.entity.evaluation.QuizAttempt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizAttemptRepository extends MongoRepository<QuizAttempt, String> {
}
