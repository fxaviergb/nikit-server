package com.teamdroid.nikit.service.execution;

import com.teamdroid.nikit.entity.execution.QuizzyExecution;
import com.teamdroid.nikit.entity.execution.QuizzyAttemptExecution;
import com.teamdroid.nikit.entity.execution.QuizAttempt;
import com.teamdroid.nikit.repository.execution.QuizzyExecutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


@Service
@Transactional
public class QuizzyExecutionService {

    @Autowired
    private QuizzyExecutionRepository quizzyExecutionRepository;

    @Autowired
    private QuizzyAttemptExecutionService quizzyAttemptExecutionService;

    @Autowired
    private QuizAttemptService quizAttemptService;


    public QuizzyExecution create(String quizId) {
        Assert.notNull(quizId, "The quiz Id cannot be null");
        QuizAttempt quizAttempt = quizAttemptService.createFromQuizBase(quizId);
        QuizzyAttemptExecution quizAttemptExecution = quizzyAttemptExecutionService.create(quizAttempt);
        QuizzyExecution quizzyExecution = QuizzyExecution.build(quizAttemptExecution);
        return save(quizzyExecution);
    }

    public QuizzyExecution save(QuizzyExecution executionQuiz) {
        Assert.notNull(executionQuiz, "The quizzy can not be null");
        return quizzyExecutionRepository.save(executionQuiz);
    }

}
