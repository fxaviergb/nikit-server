package com.teamdroid.nikit.service.execution;

import com.teamdroid.nikit.entity.execution.ExecutionQuiz;
import com.teamdroid.nikit.entity.execution.QuizAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


@Service
@Transactional
public class ExecutionQuizService {

    @Autowired
    private QuizAttemptService quizAttemptService;


    public ExecutionQuiz create(String quizId) {
        Assert.notNull(quizId, "The quiz Id cannot be null");
        QuizAttempt quizAttempt = quizAttemptService.generate(quizId);
        return ExecutionQuiz.buildWithAttempt(quizAttempt);
    }

}
