package com.teamdroid.nikit.controller.execution;

import com.teamdroid.nikit.dto.QuizzyAttemptRegisterDTO;
import com.teamdroid.nikit.dto.QuizzyExecutionDTO;
import com.teamdroid.nikit.entity.execution.QuizzyAttemptExecution;
import com.teamdroid.nikit.entity.execution.QuizzyExecution;
import com.teamdroid.nikit.mapper.QuizzyExecutionMapper;
import com.teamdroid.nikit.service.execution.QuizzyAttemptExecutionService;
import com.teamdroid.nikit.service.execution.QuizzyExecutionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/api/execution/quizzy")
@AllArgsConstructor
public class QuizzyExecutionController {

    @Autowired
    private QuizzyExecutionService quizzyExecutionService;

    @Autowired
    private QuizzyAttemptExecutionService quizzyAttemptExecutionService;

    private final QuizzyExecutionMapper quizzyExecutionMapper;

    @GetMapping("/{id}")
    public ResponseEntity<QuizzyExecutionDTO> getById(@PathVariable String id) {
        return quizzyExecutionService.getById(id)
                .map(quizzyExecutionMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{quizId}")
    public ResponseEntity<QuizzyExecutionDTO> createQuizzyExecutionFromQuiz(@PathVariable String quizId) {
        QuizzyExecution quizzyExecution = quizzyExecutionService.create(quizId);
        return ResponseEntity.ok(quizzyExecutionMapper.toDTO(quizzyExecution));
    }

    @PostMapping("/attempt/review/{attemptId}")
    public ResponseEntity<QuizzyAttemptExecution> reviewAttempt(@PathVariable String attemptId,
                                                              @RequestBody QuizzyAttemptRegisterDTO quizzyAttemptRegisterDTO) {
        QuizzyAttemptExecution quizzyAttemptExecution = quizzyAttemptExecutionService.register(attemptId, quizzyAttemptRegisterDTO);
        return ResponseEntity.ok(quizzyAttemptExecution);
    }

    @PostMapping("/attempt/evaluate/{attemptId}")
    public ResponseEntity<QuizzyAttemptExecution> evaluateAttempt(@PathVariable String attemptId) {
        QuizzyAttemptExecution quizzyAttemptExecution = quizzyAttemptExecutionService.evaluate(attemptId);
        return ResponseEntity.ok(quizzyAttemptExecution);
    }
}
