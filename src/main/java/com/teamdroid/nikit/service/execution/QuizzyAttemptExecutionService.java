package com.teamdroid.nikit.service.execution;

import com.teamdroid.nikit.entity.execution.QuizzyAttemptExecution;
import com.teamdroid.nikit.entity.execution.QuizAttempt;
import com.teamdroid.nikit.repository.execution.QuizzyAttemptExecutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


@Service
@Transactional
public class QuizzyAttemptExecutionService {

    @Autowired
    QuizzyAttemptExecutionRepository quizzyAttemptExecutionRepository;


    public QuizzyAttemptExecution create(QuizAttempt quizAttempt) {
        Assert.notNull(quizAttempt, "The quiz attempt cannot be null");
        QuizzyAttemptExecution executionQuizAttempt = new QuizzyAttemptExecution(quizAttempt);
        return save(executionQuizAttempt);
    }

    public QuizzyAttemptExecution save(QuizzyAttemptExecution executionQuizAttempt) {
        Assert.notNull(executionQuizAttempt, "The execution quiz attempt cannot be null");
        return quizzyAttemptExecutionRepository.save(executionQuizAttempt);
    }

}
