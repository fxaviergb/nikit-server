package com.teamdroid.nikit.service.evaluation;

import com.teamdroid.nikit.entity.Quiz;
import com.teamdroid.nikit.entity.evaluation.QuizAttempt;
import com.teamdroid.nikit.mapper.QuizAttemptFromQuizMapper;
import com.teamdroid.nikit.service.model.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


@Service
@Transactional
@AllArgsConstructor
public class QuizAttemptService {

    private final QuizAttemptFromQuizMapper quizAttemptFromQuizMapper;

    @Autowired
    private QuizService quizService;


    public QuizAttempt createFromQuizBase(String quizId, Integer questionCount) {
        Assert.notNull(quizId, "There is not found a Quiz with the specified Id");
        Quiz quiz = quizService.getForEvaluation(quizId, questionCount);
        return quizAttemptFromQuizMapper.from(quiz);
    }

}
