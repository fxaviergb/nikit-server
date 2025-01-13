package com.teamdroid.nikit.controller.execution;

import com.teamdroid.nikit.dto.QuizzyExecutionDTO;
import com.teamdroid.nikit.entity.execution.QuizzyExecution;
import com.teamdroid.nikit.mapper.QuizzyExecutionMapper;
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

    private final QuizzyExecutionMapper quizzyExecutionMapper;

    @GetMapping("/{quizzyExecutionId}")
    public ResponseEntity<QuizzyExecution> getById(@PathVariable String quizzyExecutionId) {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{quizId}")
    public ResponseEntity<QuizzyExecutionDTO> createQuizzyExecutionFromQuiz(@PathVariable String quizId) {
        QuizzyExecution quizzyExecution = quizzyExecutionService.create(quizId);
        return ResponseEntity.ok(quizzyExecutionMapper.toDTO(quizzyExecution));
    }
}
