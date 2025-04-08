package com.teamdroid.nikit.controller.evaluation;

import com.teamdroid.nikit.dto.EvaluationAttemptRegisterDTO;
import com.teamdroid.nikit.dto.EvaluationAttemptReviewDTO;
import com.teamdroid.nikit.dto.EvaluationAttemptReviewSummaryDTO;
import com.teamdroid.nikit.dto.EvaluationDTO;
import com.teamdroid.nikit.entity.evaluation.EvaluationAttempt;
import com.teamdroid.nikit.entity.evaluation.Evaluation;
import com.teamdroid.nikit.mapper.EvaluationAttemptMapper;
import com.teamdroid.nikit.mapper.EvaluationMapper;
import com.teamdroid.nikit.service.evaluation.EvaluationAttemptService;
import com.teamdroid.nikit.service.evaluation.EvaluationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/evaluation")
@AllArgsConstructor
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private EvaluationAttemptService evaluationAttemptService;

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
    public ResponseEntity<EvaluationDTO> createForQuiz(@PathVariable String quizId) {
        Evaluation quizzyExecution = evaluationService.create(quizId);
        return ResponseEntity.ok(evaluationMapper.toDTO(quizzyExecution));
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
}
