package com.teamdroid.nikit.dto;

import lombok.*;

import java.time.LocalDateTime;


@Data
@Builder
public class EvaluationAttemptDTO {

    private String id;
    private QuizAttemptDTO quiz;

    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private String createdBy;
    private String modifiedBy;

}
