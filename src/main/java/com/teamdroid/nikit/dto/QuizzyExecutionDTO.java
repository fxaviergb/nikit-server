package com.teamdroid.nikit.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class QuizzyExecutionDTO {

    private String id;
    private List<QuizzyAttemptExecutionDTO> quizzyAttemptExecutions = new ArrayList<>();

}
