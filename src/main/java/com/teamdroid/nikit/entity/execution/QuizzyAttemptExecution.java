package com.teamdroid.nikit.entity.execution;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "quizzy_attempt_execution")
public class QuizzyAttemptExecution {

    @Id
    private String id;
    private QuizAttempt quizAttempt;
    private LocalDateTime executionDate;

    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private String createdBy;
    private String modifiedBy;

    private Grade grade;

    public QuizzyAttemptExecution(QuizAttempt quizAttempt) {
        this.quizAttempt = quizAttempt;
        this.creationDate = LocalDateTime.now();
    }

}
