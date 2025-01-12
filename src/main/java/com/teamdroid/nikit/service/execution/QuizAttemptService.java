package com.teamdroid.nikit.service.execution;

import com.teamdroid.nikit.entity.Quiz;
import com.teamdroid.nikit.entity.execution.QuizAttempt;
import com.teamdroid.nikit.mapper.QuizAttemptFromQuizMapper;
import com.teamdroid.nikit.repository.execution.QuizAttemptRepository;
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

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private QuizService quizService;

    private final QuizAttemptFromQuizMapper quizAttemptFromQuizMapper;

    public QuizAttempt generate(String quizId) {
        Quiz quiz = quizService.findById(quizId);
        Assert.notNull(quiz, "There is not found a Quiz with the specified Id");
        return quizAttemptFromQuizMapper.toDTO(quiz);
    }
}
