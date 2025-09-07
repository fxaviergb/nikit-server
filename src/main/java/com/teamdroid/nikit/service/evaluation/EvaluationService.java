package com.teamdroid.nikit.service.evaluation;

import com.teamdroid.nikit.entity.Audit;
import com.teamdroid.nikit.entity.Quiz;
import com.teamdroid.nikit.entity.evaluation.Evaluation;
import com.teamdroid.nikit.entity.evaluation.EvaluationAttempt;
import com.teamdroid.nikit.entity.evaluation.QuizAttempt;
import com.teamdroid.nikit.repository.execution.EvaluationRepository;
import com.teamdroid.nikit.service.model.QuizService;
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


    public Evaluation create(String quizId, String userId, Integer questionCount) {
        Assert.notNull(quizId, "The quiz Id cannot be null");
        QuizAttempt quizAttempt = quizAttemptService.createFromQuizBase(quizId, questionCount);
        Evaluation evaluation = save(new Evaluation(quizAttempt, userId));
        EvaluationAttempt evaluationAttempt = evaluationAttemptService.create(evaluation, quizAttempt);
        evaluation.addQuizzyAttemptExecution(evaluationAttempt);

        return save(evaluation);
    }

    public Evaluation save(Evaluation evaluation) {
        Assert.notNull(evaluation, "The evaluation can not be null");
        return evaluationRepository.save(evaluation);
    }

    public Optional<Evaluation> getById(String id) {
        return evaluationRepository.findById(id);
    }

}
