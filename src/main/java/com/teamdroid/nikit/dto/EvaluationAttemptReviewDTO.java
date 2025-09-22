package com.teamdroid.nikit.dto;

import com.teamdroid.nikit.entity.evaluation.QuizAttemptRequestSource;
import com.teamdroid.nikit.model.enumeration.QuizAttemptType;
import lombok.Data;

import java.util.List;


@Data
public class EvaluationAttemptReviewDTO {

    private String attemptId;
    private String quizId;
    private QuizAttemptType quizType;
    private List<QuestionReviewDTO> review;
    private EvaluationAttemptReviewGradeDTO grade;
    private QuizAttemptRequestSource requestSource;

}
