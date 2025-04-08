package com.teamdroid.nikit.dto;

import lombok.Data;

import java.util.List;


@Data
public class QuestionReviewDTO {

    private String id;
    private String question;
    private List<OptionReviewDTO> options;
    private QuestionReviewSummaryDTO review;

}
