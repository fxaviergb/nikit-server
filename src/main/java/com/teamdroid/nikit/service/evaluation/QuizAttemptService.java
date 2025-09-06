package com.teamdroid.nikit.service.evaluation;

import com.teamdroid.nikit.entity.Quiz;
import com.teamdroid.nikit.entity.evaluation.QuizAttempt;
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

    public QuizAttempt createFromQuizBase(String quizId) {
        Quiz quiz = quizService.findByIdFull(quizId);
        Assert.notNull(quiz, "There is not found a Quiz with the specified Id");
        QuizAttempt quizAttempt = quizAttemptFromQuizMapper.from(quiz);
        quizAttempt.setIdBase(quiz.getId());
        return save(quizAttempt);
    }

    public QuizAttempt save(QuizAttempt quizAttempt) {
        Assert.notNull(quizAttempt, "The quiz attempt can not be null");
        return quizAttemptRepository.save(quizAttempt);
    }
}
