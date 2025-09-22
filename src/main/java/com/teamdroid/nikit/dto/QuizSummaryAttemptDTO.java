package com.teamdroid.nikit.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuizSummaryAttemptDTO {

    private String id;
    private Double grade;
    private Double maxGrade;
    private Double efficiencyPercentage;
    private LocalDateTime date;


}
