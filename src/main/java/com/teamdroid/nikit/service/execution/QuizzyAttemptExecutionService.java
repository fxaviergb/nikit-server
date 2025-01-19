package com.teamdroid.nikit.service.execution;

import com.teamdroid.nikit.dto.QuizzyAttemptRegisterDTO;
import com.teamdroid.nikit.entity.execution.*;
import com.teamdroid.nikit.repository.execution.QuizzyAttemptExecutionRepository;
import com.teamdroid.nikit.service.manager.QuizzyManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


@Service
@Transactional
public class QuizzyAttemptExecutionService {

    @Autowired
    QuizzyAttemptExecutionRepository quizzyAttemptExecutionRepository;

    @Autowired
    QuizzyManagerService quizzyManagerService;


    public QuizzyAttemptExecution create(QuizAttempt quizAttempt) {
        Assert.notNull(quizAttempt, "The quiz attempt cannot be null");
        QuizzyAttemptExecution executionQuizAttempt = new QuizzyAttemptExecution(quizAttempt);
        return save(executionQuizAttempt);
    }

    public QuizzyAttemptExecution save(QuizzyAttemptExecution executionQuizAttempt) {
        Assert.notNull(executionQuizAttempt, "The execution quiz attempt cannot be null");
        return quizzyAttemptExecutionRepository.save(executionQuizAttempt);
    }

    public QuizzyAttemptExecution getById(String quizzyAttemptExecutionId) {
        return quizzyAttemptExecutionRepository.findById(quizzyAttemptExecutionId).orElseThrow(
                () -> new RuntimeException("QuizzyAttemptExecution not found"));
    }

    public QuizzyAttemptExecution register(String attemptId, QuizzyAttemptRegisterDTO quizzyAttemptRegisterDTO) {
        Assert.notNull(attemptId, "The attempt id cannot be null");
        Assert.notNull(quizzyAttemptRegisterDTO, "The attempt data cannot be null");

        QuizzyAttemptExecution quizzyAttemptExecution = getById(attemptId);
        quizzyManagerService.review(quizzyAttemptExecution, quizzyAttemptRegisterDTO);

        return quizzyAttemptExecutionRepository.save(quizzyAttemptExecution);
    }

    public QuizzyAttemptExecution evaluate(String attemptId) {
        Assert.notNull(attemptId, "The attempt id cannot be null");

        QuizzyAttemptExecution quizzyAttemptExecution = getById(attemptId);
        quizzyManagerService.evaluate(quizzyAttemptExecution);

        return quizzyAttemptExecutionRepository.save(quizzyAttemptExecution);
    }

}
