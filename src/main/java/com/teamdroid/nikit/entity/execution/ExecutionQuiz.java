package com.teamdroid.nikit.entity.execution;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "execution_quiz")
public class ExecutionQuiz {

    @Id
    private String id;
    private QuizAttempt quizAttempt;
    private List<ExecutionQuizAttempt> executionQuizAttempts = new ArrayList<>();

    public static ExecutionQuiz buildWithAttempt(QuizAttempt quizAttempt) {
        ExecutionQuiz executionQuiz = new ExecutionQuiz();
        executionQuiz.addExecutionAttempt(new ExecutionQuizAttempt(quizAttempt));
        executionQuiz.quizAttempt = quizAttempt;
        return executionQuiz;
    }

    public void addExecutionAttempt(ExecutionQuizAttempt executionQuizAttempt) {
        if (!Objects.isNull(executionQuizAttempt)) {
            if (Objects.isNull(this.executionQuizAttempts)) {
                this.executionQuizAttempts = new ArrayList<>();
            }
            executionQuizAttempts.forEach(o -> {
                if (!this.executionQuizAttempts.contains(o)) {
                    this.executionQuizAttempts.add(o);
                }
            });
        }
    }

}
