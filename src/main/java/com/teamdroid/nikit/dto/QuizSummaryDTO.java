package com.teamdroid.nikit.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuizSummaryDTO {
    private String id;
    private String name;
    private String description;
    private Double efficiencyPercentage;

    private QuizSummaryMetadataDTO metadata;
    private List<QuizSummaryAttemptDTO> attempts;
}
