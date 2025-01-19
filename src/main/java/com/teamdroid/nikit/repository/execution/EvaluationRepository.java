package com.teamdroid.nikit.repository.execution;

import com.teamdroid.nikit.entity.evaluation.Evaluation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends MongoRepository<Evaluation, String> {
}
