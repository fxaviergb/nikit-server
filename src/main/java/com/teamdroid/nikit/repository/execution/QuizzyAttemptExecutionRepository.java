package com.teamdroid.nikit.repository.execution;

import com.teamdroid.nikit.entity.execution.QuizzyAttemptExecution;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizzyAttemptExecutionRepository extends MongoRepository<QuizzyAttemptExecution, String> {
}
