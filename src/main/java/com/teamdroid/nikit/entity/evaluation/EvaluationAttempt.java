package com.teamdroid.nikit.entity.evaluation;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "evaluation_attempt")
public class EvaluationAttempt {

    @Id
    private String id;
    private QuizAttempt quiz;
    private LocalDateTime executionDate;

    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private String createdBy;
    private String modifiedBy;

    private Grade grade;

    public EvaluationAttempt(QuizAttempt quiz) {
        this.quiz = quiz;
        this.creationDate = LocalDateTime.now();
    }

}
