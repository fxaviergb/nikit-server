package com.teamdroid.nikit.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class EvaluationAttemptReviewSummaryGradeDTO {

    private Double qualification;
    private Double maxQualification;
    private LocalDateTime reviewDate;

}
