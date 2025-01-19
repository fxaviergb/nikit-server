package com.teamdroid.nikit.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
public class QuizzyAttemptRegisterDTO {

    private String id;
    private LocalDateTime executionDate;

    private List<QuestionAttemptRegisterDTO> questions = new ArrayList<>();

}
