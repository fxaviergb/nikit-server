package com.teamdroid.nikit.service.evaluation;

import com.teamdroid.nikit.entity.evaluation.Evaluation;
import com.teamdroid.nikit.entity.evaluation.EvaluationAttempt;
import com.teamdroid.nikit.entity.evaluation.QuizAttempt;
import com.teamdroid.nikit.repository.execution.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;


@Service
@Transactional
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EvaluationAttemptService evaluationAttemptService;

    @Autowired
    private QuizAttemptService quizAttemptService;


    public Evaluation create(String quizId) {
        Assert.notNull(quizId, "The quiz Id cannot be null");
        QuizAttempt quizAttempt = quizAttemptService.createFromQuizBase(quizId);
        EvaluationAttempt quizAttemptExecution = evaluationAttemptService.create(quizAttempt);
        Evaluation quizzyExecution = Evaluation.build(quizAttemptExecution);
        return save(quizzyExecution);
    }

    public Evaluation save(Evaluation evaluation) {
        Assert.notNull(evaluation, "The evaluation can not be null");
        return evaluationRepository.save(evaluation);
    }

    public Optional<Evaluation> getById(String id) {
        return evaluationRepository.findById(id);
    }

}
