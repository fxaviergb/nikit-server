package com.teamdroid.nikit.repository.model;

import com.teamdroid.nikit.entity.Question;

import java.util.List;
import java.util.Set;

public interface QuestionRepositoryCustom {
    List<Question> findRandomByQuizId(String quizId, Integer questionCount);
    List<Question> findRandomByQuizIds(Set<String> quizIds, Integer questionCount);
}
