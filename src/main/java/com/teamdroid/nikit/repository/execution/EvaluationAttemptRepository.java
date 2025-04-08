package com.teamdroid.nikit.repository.execution;

import com.teamdroid.nikit.entity.evaluation.EvaluationAttempt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationAttemptRepository extends MongoRepository<EvaluationAttempt, String> {

    List<EvaluationAttempt> findByQuiz_IdBase(String idBase);

}
