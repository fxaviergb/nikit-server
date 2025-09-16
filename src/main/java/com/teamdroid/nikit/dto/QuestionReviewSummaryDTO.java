package com.teamdroid.nikit.dto;

import lombok.Data;

import java.util.List;


@Data
public class QuestionReviewSummaryDTO {

    private String status;
    private String feedback;
    private List<String> extras;
    private String points;

}
