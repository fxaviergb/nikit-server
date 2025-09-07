package com.teamdroid.nikit.controller;

import com.teamdroid.nikit.dto.QuizSummaryDTO;
import com.teamdroid.nikit.dto.request.QuestionRequest;
import com.teamdroid.nikit.dto.request.QuizRequest;
import com.teamdroid.nikit.entity.Audit;
import com.teamdroid.nikit.entity.Quiz;
import com.teamdroid.nikit.mapper.QuizSummaryMapper;
import com.teamdroid.nikit.model.view.QuizSummary;
import com.teamdroid.nikit.service.model.QuestionService;
import com.teamdroid.nikit.service.model.QuizService;
import com.teamdroid.nikit.service.model.TopicService;
import com.teamdroid.nikit.shared.audit.AuditFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz")
@AllArgsConstructor
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private QuestionService questionService;

    private final QuizSummaryMapper quizSummaryMapper;

    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable String quizId) {
        Quiz quiz = quizService.findById(quizId);
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/{topicId}")
    public ResponseEntity<Void> createQuizForTopic(
            @PathVariable String topicId,
            @RequestBody QuizRequest quizRequest
    ) {
        topicService.findById(topicId); // Lanza NotFoundException si no existe
        String userId = "system"; // TODO Obtener de JWT
        quizService.createQuizWithChildren(quizRequest, topicId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{quizId}/questions")
    public ResponseEntity<Quiz> addQuestionsToQuiz(
            @PathVariable String quizId,
            @RequestBody List<QuestionRequest> questions
    ) {
        Quiz quiz = quizService.findById(quizId); // lanza excepción si no existe
        String userId = "system"; // TODO Obtener de JWT
        for (QuestionRequest questionReq : questions) {
            questionService.createQuestionWithOptions(questionReq, quiz.getId(), userId);
        }
        Quiz updatedQuiz = quizService.findById(quizId);
        return ResponseEntity.ok(updatedQuiz);
    }

    @GetMapping("/{quizId}/summary")
    public ResponseEntity<QuizSummaryDTO> getQuizSummaryByQuizId(@PathVariable String quizId) {
        QuizSummary quizSummary = quizService.findSummaryById(quizId);
        QuizSummaryDTO quizSummaryDTO = quizSummaryMapper.toQuizSummaryDTO(quizSummary);
        return ResponseEntity.ok(quizSummaryDTO);
    }
}
