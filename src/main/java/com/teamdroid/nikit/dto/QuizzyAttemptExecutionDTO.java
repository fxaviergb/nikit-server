package com.teamdroid.nikit.dto;

import lombok.*;

import java.time.LocalDateTime;


@Data
@Builder
public class QuizzyAttemptExecutionDTO {

    private String id;
    private QuizAttemptDTO quizAttempt;

    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private String createdBy;
    private String modifiedBy;

}
