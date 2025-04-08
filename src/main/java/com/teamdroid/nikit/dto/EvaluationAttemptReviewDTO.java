package com.teamdroid.nikit.dto;

import lombok.Data;

import java.util.List;


@Data
public class EvaluationAttemptReviewDTO {

    private String attemptId;
    private String quizId;
    private List<QuestionReviewDTO> review;
    private EvaluationAttemptReviewGradeDTO grade;

}
