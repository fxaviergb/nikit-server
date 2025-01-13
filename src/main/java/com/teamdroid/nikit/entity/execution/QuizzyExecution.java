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
@Document(collection = "quizzy_execution")
public class QuizzyExecution {

    @Id
    private String id;
    private QuizAttempt quizAttempt;
    private List<QuizzyAttemptExecution> quizzyAttemptExecutions = new ArrayList<>();

    public static QuizzyExecution build(QuizzyAttemptExecution executionQuizAttempt) {
        QuizzyExecution executionQuiz = new QuizzyExecution();
        executionQuiz.addQuizzyAttemptExecution(executionQuizAttempt);
        executionQuiz.quizAttempt = executionQuizAttempt.getQuizAttempt();
        return executionQuiz;
    }

    public void addQuizzyAttemptExecution(QuizzyAttemptExecution quizzyAttemptExecution) {
        if (!Objects.isNull(quizzyAttemptExecution)) {
            if (Objects.isNull(this.quizzyAttemptExecutions)) {
                this.quizzyAttemptExecutions = new ArrayList<>();
            }
            if (!this.quizzyAttemptExecutions.contains(quizzyAttemptExecution)) {
                this.quizzyAttemptExecutions.add(quizzyAttemptExecution);
            }
        }
    }

}
