package com.teamdroid.nikit.entity.evaluation;

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
@Document(collection = "evaluation")
public class Evaluation {

    @Id
    private String id;
    private QuizAttempt quiz;
    private List<EvaluationAttempt> attempts = new ArrayList<>();

    public static Evaluation build(EvaluationAttempt attempt) {
        Evaluation executionQuiz = new Evaluation();
        executionQuiz.addQuizzyAttemptExecution(attempt);
        executionQuiz.setQuiz(attempt.getQuiz());
        return executionQuiz;
    }

    public void addQuizzyAttemptExecution(EvaluationAttempt quizzyAttemptExecution) {
        if (!Objects.isNull(quizzyAttemptExecution)) {
            if (Objects.isNull(this.attempts)) {
                this.attempts = new ArrayList<>();
            }
            if (!this.attempts.contains(quizzyAttemptExecution)) {
                this.attempts.add(quizzyAttemptExecution);
            }
        }
    }

}
