package com.teamdroid.nikit.dto.request;

import com.teamdroid.nikit.entity.evaluation.QuizAttemptRequestParams;
import com.teamdroid.nikit.entity.evaluation.QuizAttemptRequestSource;
import lombok.Data;

@Data
public class EvaluationMixedCreationRequestDTO {

    private QuizAttemptRequestSource source;
    private QuizAttemptRequestParams params;

}