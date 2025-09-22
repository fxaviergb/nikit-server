package com.teamdroid.nikit.repository.model;

import com.teamdroid.nikit.entity.evaluation.EvaluationAttempt;

import java.util.List;
import java.util.Set;

public interface EvaluationAttemptRepositoryCustom {
    List<EvaluationAttempt> searchAttemptsBySource(Set<String> quizIds, String queryType, String userId);
}
