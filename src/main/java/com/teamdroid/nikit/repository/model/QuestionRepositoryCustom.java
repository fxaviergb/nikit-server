package com.teamdroid.nikit.repository.model;

import com.teamdroid.nikit.entity.Question;

import java.util.List;

public interface QuestionRepositoryCustom {
    List<Question> findRandomByQuizId(String quizId, Integer questionCount);
}
