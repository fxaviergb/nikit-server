package com.teamdroid.nikit.service.model;

import com.teamdroid.nikit.dto.request.QuestionRequest;
import com.teamdroid.nikit.dto.request.QuizRequest;
import com.teamdroid.nikit.entity.*;
import com.teamdroid.nikit.entity.evaluation.EvaluationAttempt;
import com.teamdroid.nikit.mapper.QuizMapper;
import com.teamdroid.nikit.model.view.QuizSummary;
import com.teamdroid.nikit.repository.model.QuizRepository;
import com.teamdroid.nikit.service.evaluation.EvaluationAttemptService;
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


    public void createQuizWithChildren(QuizRequest request, String topicId, String userId, Audit audit) {
        Quiz quiz = quizMapper.toEntity(request);
        quiz.setTopicIds(List.of(topicId));
        quiz.setUserId(userId);
        quiz.setVersion(1);
        quiz.setAudit(audit);
        quiz = quizRepository.save(quiz);

        for (QuestionRequest q : request.getQuestions()) {
            questionService.createQuestionWithOptions(q, quiz.getId(), userId, audit);
        }
    }

    public Quiz createQuestions(String quizId, List<QuestionRequest> questions, String userId, Audit audit) {
        Quiz quiz = findById(quizId); // Lanza excepción si no existe

        for (QuestionRequest questionReq : questions) {
            questionService.createQuestionWithOptions(questionReq, quizId, userId, audit);
        }

        // Opcional: recargar quiz si deseas ver cambios reflejados
        return findById(quizId);
    }

    public List<Quiz> findByTopicId(String topicId) {
        return quizRepository.findByTopicIdsContaining(topicId);
    }

    public Quiz findById(String quizId) {
        return quizRepository.findById(quizId).orElseThrow(
                () -> new RuntimeException("Quiz not found"));
    }

    public Quiz findByIdFull(String quizId) {
        Quiz quiz = findById(quizId);
        quiz.setQuestions(questionService.findByQuizIdFull(quizId));
        return quiz;
    }

    public QuizSummary findSummaryById(String quizId) {
        Quiz quiz = findByIdFull(quizId);
        List<EvaluationAttempt> evaluationAttempts = evaluationAttemptService.getByQuizIdBase(quizId);
        return QuizSummary.build(quiz, evaluationAttempts);
    }
}
