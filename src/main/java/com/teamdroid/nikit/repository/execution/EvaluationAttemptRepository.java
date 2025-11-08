package com.teamdroid.nikit.repository.execution;

import com.teamdroid.nikit.entity.evaluation.EvaluationAttempt;
import com.teamdroid.nikit.repository.model.EvaluationAttemptRepositoryCustom;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationAttemptRepository extends MongoRepository<EvaluationAttempt, String>, EvaluationAttemptRepositoryCustom {
    List<EvaluationAttempt> findByQuiz_Id(String id);
    List<EvaluationAttempt> findByQuiz_Id(String id, Sort sort);

}
