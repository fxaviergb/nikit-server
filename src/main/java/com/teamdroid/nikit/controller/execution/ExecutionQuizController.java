package com.teamdroid.nikit.controller.execution;

import com.teamdroid.nikit.entity.execution.ExecutionQuiz;
import com.teamdroid.nikit.service.execution.ExecutionQuizService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/api/execution/quiz")
@AllArgsConstructor
public class ExecutionQuizController {

    @Autowired
    private ExecutionQuizService executionQuizService;

    @GetMapping("/{executionQuizId}")
    public ResponseEntity<ExecutionQuiz> createQuizForTopic(@PathVariable String executionQuizId) {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{quizId}")
    public ResponseEntity<ExecutionQuiz> generateExecutionQuiz(@PathVariable String quizId) {
        ExecutionQuiz executionQuiz = executionQuizService.create(quizId);
        return ResponseEntity.ok(executionQuiz);
    }
}
