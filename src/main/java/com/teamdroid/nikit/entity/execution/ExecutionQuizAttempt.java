package com.teamdroid.nikit.entity.execution;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "execution_quiz_attempt")
public class ExecutionQuizAttempt {

    @Id
    private String id;
    private QuizAttempt quizAttempt;

    private String creationDate;
    private String modificationDate;
    private String createdBy;
    private String modifiedBy;

    public ExecutionQuizAttempt(QuizAttempt quizAttempt) {
        this.quizAttempt = quizAttempt;
    }
}
