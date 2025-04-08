package com.teamdroid.nikit.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class EvaluationAttemptReviewGradeDTO {

    private String qualification;
    private String maxQualification;
    private LocalDateTime reviewDate;

}
