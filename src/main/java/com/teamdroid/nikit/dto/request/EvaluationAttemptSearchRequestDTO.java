package com.teamdroid.nikit.dto.request;

import com.teamdroid.nikit.entity.evaluation.QuizAttemptRequestSource;
import lombok.Data;

@Data
public class EvaluationAttemptSearchRequestDTO {
    private String queryType;
    private QuizAttemptRequestSource source;
}
