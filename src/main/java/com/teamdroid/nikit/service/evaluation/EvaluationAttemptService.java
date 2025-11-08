package com.teamdroid.nikit.service.evaluation;

import com.teamdroid.nikit.dto.EvaluationAttemptRegisterDTO;
import com.teamdroid.nikit.entity.evaluation.*;
import com.teamdroid.nikit.repository.execution.EvaluationAttemptRepository;
import com.teamdroid.nikit.service.evaluation.manager.EvaluationManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;


@Service
@Transactional
public class EvaluationAttemptService {

    @Autowired
    EvaluationAttemptRepository evaluationAttemptRepository;

    @Autowired
    EvaluationManagerService evaluationManagerService;

    @Autowired
    private QuizAttemptService quizAttemptService;


    public EvaluationAttempt create(Evaluation evaluation, QuizAttempt quizAttempt) {
        Assert.notNull(quizAttempt, "The quiz attempt cannot be null");
        EvaluationAttempt executionQuizAttempt = new EvaluationAttempt(evaluation, quizAttempt);
        return save(executionQuizAttempt);
    }

    public EvaluationAttempt save(EvaluationAttempt evaluationAttempt) {
        Assert.notNull(evaluationAttempt, "The evaluation attempt cannot be null");
        return evaluationAttemptRepository.save(evaluationAttempt);
    }

    public EvaluationAttempt getById(String evaluationAttemptId) {
        return evaluationAttemptRepository.findById(evaluationAttemptId).orElseThrow(
                () -> new RuntimeException("Evaluation attempt not found"));
    }

    public List<EvaluationAttempt> getByQuizId(String quizId) {
        Assert.notNull(quizId, "The quizId cannot be null");
        return evaluationAttemptRepository.findByQuiz_Id(
                quizId, Sort.by(Sort.Direction.DESC, "executionDate"));
    }


    public EvaluationAttempt register(String attemptId, EvaluationAttemptRegisterDTO evaluationAttemptRegisterDTO) {
        Assert.notNull(attemptId, "The attempt id cannot be null");
        Assert.notNull(evaluationAttemptRegisterDTO, "The attempt data cannot be null");

        EvaluationAttempt evaluationAttempt = getById(attemptId);
        evaluationManagerService.review(evaluationAttempt, evaluationAttemptRegisterDTO);
        return evaluationAttemptRepository.save(evaluationAttempt);
    }

    public EvaluationAttempt evaluate(String attemptId) {
        Assert.notNull(attemptId, "The attempt id cannot be null");

        EvaluationAttempt evaluationAttempt = getById(attemptId);
        evaluationManagerService.evaluate(evaluationAttempt);
        return evaluationAttemptRepository.save(evaluationAttempt);
    }

    public List<EvaluationAttempt> searchBySource(String queryType, QuizAttemptRequestSource source, String userId) {
        Set<String> allQuizIds = quizAttemptService.findQuizIdsFromAttemptSources(
                source.getKnowledges(),
                source.getTopics(),
                source.getQuizzes()
        );
        return evaluationAttemptRepository.searchAttemptsBySource(allQuizIds, queryType, userId);
    }


}
