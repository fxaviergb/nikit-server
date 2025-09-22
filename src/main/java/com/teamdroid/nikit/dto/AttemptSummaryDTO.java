package com.teamdroid.nikit.dto;

import lombok.Data;

import java.util.List;

@Data
public class AttemptSummaryDTO {
    private Double efficiencyPercentage;
    private List<QuizSummaryAttemptDTO> attempts;
}
