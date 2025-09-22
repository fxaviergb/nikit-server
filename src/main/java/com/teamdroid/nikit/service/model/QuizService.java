package com.teamdroid.nikit.service.model;

import com.teamdroid.nikit.dto.request.QuestionRequest;
import com.teamdroid.nikit.dto.request.QuizRequest;
import com.teamdroid.nikit.entity.*;
import com.teamdroid.nikit.entity.evaluation.EvaluationAttempt;
import com.teamdroid.nikit.mapper.QuizMapper;
import com.teamdroid.nikit.model.view.QuizSummary;
import com.teamdroid.nikit.repository.model.QuizRepository;
import com.teamdroid.nikit.service.evaluation.EvaluationAttemptService;
import com.teamdroid.nikit.shared.audit.AuditFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@AllArgsConstructor
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private EvaluationAttemptService evaluationAttemptService;

    private final QuizMapper quizMapper;


    public void createQuizWithRelations(QuizRequest request, String topicId, String userId) {
        Audit audit = AuditFactory.create(userId);
        Quiz quiz = quizMapper.toEntity(request);
        quiz.setTopicIds(List.of(topicId));
        quiz.setVersion(1);
        quiz.setAudit(audit);
        quiz = quizRepository.save(quiz);

        for (QuestionRequest q : request.getQuestions()) {
            questionService.createQuestionWithRelations(q, quiz.getId(), userId);
        }
    }

    public List<Quiz> findByTopicId(String topicId) {
        return quizRepository.findByTopicIdsContaining(topicId);
    }

    public List<Quiz> findByTopicIds(List<String> topicIds) {
        return quizRepository.findByTopicIdsIn(topicIds);
    }

    public Quiz findById(String quizId) {
        return quizRepository.findById(quizId).orElseThrow(
                () -> new RuntimeException("Quiz not found"));
    }

    public List<Quiz> findByIds(List<String> quizIds) {
        return quizRepository.findByIdIn(quizIds);
    }

    public Quiz findByIdFull(String quizId) {
        Quiz quiz = findById(quizId);
        quiz.setQuestions(questionService.findByQuizIdFull(quizId));
        return quiz;
    }

    public QuizSummary findSummaryById(String quizId) {
        Quiz quiz = findByIdFull(quizId);
        List<EvaluationAttempt> evaluationAttempts = evaluationAttemptService.getByQuizId(quizId);
        return QuizSummary.build(quiz, evaluationAttempts);
    }

    public Quiz getForEvaluation(String quizId, Integer questionCount) {
        Quiz quiz = findById(quizId);
        quiz.setQuestions(questionService.getQuestionsForQuizEvaluation(quizId, questionCount));
        return quiz;
    }

    public Quiz updateQuizWithRelations(String quizId, QuizRequest dto, String userId) {
        Quiz quiz = findById(quizId);

        quiz.setName(dto.getName());
        quiz.setDescription(dto.getDescription());
        quiz.setAudit(AuditFactory.update(quiz.getAudit(), userId));

        // Incrementar versión del cuestionario
        Integer currentVersion = quiz.getVersion();
        quiz.setVersion(currentVersion == null ? 1 : currentVersion + 1);

        quizRepository.save(quiz);
        questionService.updateQuestionsWithRelations(quizId, dto.getQuestions(), userId);

        return findByIdFull(quizId);
    }

}
