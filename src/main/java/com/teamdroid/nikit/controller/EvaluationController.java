package com.teamdroid.nikit.controller;

import com.teamdroid.nikit.dto.*;
import com.teamdroid.nikit.dto.request.EvaluationAttemptSearchRequestDTO;
import com.teamdroid.nikit.dto.request.EvaluationMixedCreationRequestDTO;
import com.teamdroid.nikit.entity.evaluation.EvaluationAttempt;
import com.teamdroid.nikit.entity.evaluation.Evaluation;
import com.teamdroid.nikit.mapper.AttemptSummaryMapper;
import com.teamdroid.nikit.mapper.EvaluationAttemptMapper;
import com.teamdroid.nikit.mapper.EvaluationMapper;
import com.teamdroid.nikit.mapper.QuizSummaryAttemptMapper;
import com.teamdroid.nikit.service.evaluation.EvaluationAttemptService;
import com.teamdroid.nikit.service.evaluation.EvaluationService;
import com.teamdroid.nikit.service.security.AuthenticatedUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/evaluation")
@AllArgsConstructor
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private EvaluationAttemptService evaluationAttemptService;

    @Autowired
    private final AuthenticatedUserService authenticatedUserService;

    @Autowired
    private QuizSummaryAttemptMapper quizSummaryAttemptMapper;

    @Autowired
    private AttemptSummaryMapper attemptSummaryMapper;

    private final EvaluationMapper evaluationMapper;

    private final EvaluationAttemptMapper evaluationAttemptMapper;


    @GetMapping("/{id}")
    public ResponseEntity<EvaluationDTO> getById(@PathVariable String id) {
        return evaluationService.getById(id)
                .map(evaluationMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/create/{quizId}")
    public ResponseEntity<EvaluationDTO> createForQuiz(
            @PathVariable String quizId,
            @RequestParam(name = "questionCount", required = false) Integer questionCount) {
        String userId = authenticatedUserService.getUserId();
        Evaluation evaluation = evaluationService.create(quizId, userId, questionCount);
        return ResponseEntity.ok(evaluationMapper.toDTO(evaluation));
    }

    @PostMapping("/create/mixed")
    public ResponseEntity<EvaluationDTO> createMixedEvaluation(@RequestBody EvaluationMixedCreationRequestDTO request) {
        String userId = authenticatedUserService.getUserId();
        Evaluation evaluation = evaluationService.createMixed(
                request.getSource(),
                request.getParams(),
                userId
        );
        return ResponseEntity.ok(evaluationMapper.toDTO(evaluation));
    }

    @GetMapping("/attempt/{attemptId}")
    public ResponseEntity<EvaluationAttemptReviewDTO> getAttemptByAttemptId(@PathVariable String attemptId) {
        EvaluationAttempt evaluationAttempt = evaluationAttemptService.getById(attemptId);
        EvaluationAttemptReviewDTO evaluationAttemptReviewDTO = evaluationAttemptMapper.toDetailedReviewDto(evaluationAttempt);
        return ResponseEntity.ok(evaluationAttemptReviewDTO);
    }

    @PostMapping("/attempt/review/{attemptId}")
    public ResponseEntity<EvaluationAttemptReviewSummaryDTO> reviewAttempt(@PathVariable String attemptId,
                                                                           @RequestBody EvaluationAttemptRegisterDTO evaluationAttemptRegisterDTO) {
        EvaluationAttempt evaluationAttempt = evaluationAttemptService.register(attemptId, evaluationAttemptRegisterDTO);
        EvaluationAttemptReviewSummaryDTO evaluationAttemptReviewDTO = evaluationAttemptMapper.toEvaluationAttemptReviewSummaryDTO(evaluationAttempt);
        return ResponseEntity.ok(evaluationAttemptReviewDTO);
    }

    @PostMapping("/attempt/evaluate/{attemptId}")
    public ResponseEntity<EvaluationAttempt> evaluateAttempt(@PathVariable String attemptId) {
        EvaluationAttempt quizzyAttemptExecution = evaluationAttemptService.evaluate(attemptId);
        return ResponseEntity.ok(quizzyAttemptExecution);
    }

    @PostMapping("/attempt/search")
    public ResponseEntity<List<EvaluationAttemptReviewDTO>> searchAttemptsBySource(
            @RequestBody EvaluationAttemptSearchRequestDTO request) {
        //String userId = authenticatedUserService.getUserId();
        String userId = null; // TODO Get all users for now
        List<EvaluationAttempt> attempts = evaluationAttemptService.searchBySource(
                request.getQueryType(),
                request.getSource(),
                userId
        );
        List<EvaluationAttemptReviewDTO> response = attempts.stream()
                .map(evaluationAttemptMapper::toDetailedReviewDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/attempt/search/summary")
    public ResponseEntity<AttemptSummaryDTO> searchSummaryAttemptsBySource(
            @RequestBody EvaluationAttemptSearchRequestDTO request) {
        //String userId = authenticatedUserService.getUserId();
        String userId = null; // TODO: traer usuario real más adelante
        List<EvaluationAttempt> attempts = evaluationAttemptService.searchBySource(
                request.getQueryType(),
                request.getSource(),
                userId
        );
        List<QuizSummaryAttemptDTO> attemptDTOs = attempts.stream()
                .map(quizSummaryAttemptMapper::toDTO)
                .toList();
        AttemptSummaryDTO summary = attemptSummaryMapper.toSummary(attemptDTOs);
        return ResponseEntity.ok(summary);
    }


}
