package com.teamdroid.nikit.dto;

import lombok.Data;


@Data
public class EvaluationAttemptReviewSummaryDTO {

    private String attemptId;
    private String quizId;
    private EvaluationAttemptReviewSummaryGradeDTO grade;

}
