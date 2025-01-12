package com.teamdroid.nikit.repository.execution;

import com.teamdroid.nikit.entity.execution.ExecutionQuiz;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionQuizRepository extends MongoRepository<ExecutionQuiz, String> {
}
