package com.teamdroid.nikit.controller.evaluation;

import com.teamdroid.nikit.dto.EvaluationAttemptRegisterDTO;
import com.teamdroid.nikit.dto.EvaluationDTO;
import com.teamdroid.nikit.entity.evaluation.EvaluationAttempt;
import com.teamdroid.nikit.entity.evaluation.Evaluation;
import com.teamdroid.nikit.mapper.EvaluationMapper;
import com.teamdroid.nikit.service.evaluation.EvaluationAttemptService;
import com.teamdroid.nikit.service.evaluation.EvaluationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/api/evaluation")
@AllArgsConstructor
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private EvaluationAttemptService evaluationAttemptService;

    private final EvaluationMapper evaluationMapper;

    @GetMapping("/{id}")
    public ResponseEntity<EvaluationDTO> getById(@PathVariable String id) {
        return evaluationService.getById(id)
                .map(evaluationMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create/{quizId}")
    public ResponseEntity<EvaluationDTO> createForQuiz(@PathVariable String quizId) {
        Evaluation quizzyExecution = evaluationService.create(quizId);
        return ResponseEntity.ok(evaluationMapper.toDTO(quizzyExecution));
    }

    @PostMapping("/attempt/review/{attemptId}")
    public ResponseEntity<EvaluationAttempt> reviewAttempt(@PathVariable String attemptId,
                                                           @RequestBody EvaluationAttemptRegisterDTO evaluationAttemptRegisterDTO) {
        EvaluationAttempt quizzyAttemptExecution = evaluationAttemptService.register(attemptId, evaluationAttemptRegisterDTO);
        return ResponseEntity.ok(quizzyAttemptExecution);
    }

    @PostMapping("/attempt/evaluate/{attemptId}")
    public ResponseEntity<EvaluationAttempt> evaluateAttempt(@PathVariable String attemptId) {
        EvaluationAttempt quizzyAttemptExecution = evaluationAttemptService.evaluate(attemptId);
        return ResponseEntity.ok(quizzyAttemptExecution);
    }
}
