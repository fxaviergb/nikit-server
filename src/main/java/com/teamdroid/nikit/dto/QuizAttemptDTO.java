package com.teamdroid.nikit.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class QuizAttemptDTO {

    private String id;
    private List<QuestionAttemptDTO> questions = new ArrayList<>();
}
