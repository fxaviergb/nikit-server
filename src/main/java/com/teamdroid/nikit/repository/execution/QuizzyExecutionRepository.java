package com.teamdroid.nikit.repository.execution;

import com.teamdroid.nikit.entity.execution.QuizzyExecution;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizzyExecutionRepository extends MongoRepository<QuizzyExecution, String> {
}
