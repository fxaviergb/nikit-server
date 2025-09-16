package com.teamdroid.nikit.dto;

import lombok.Data;

import java.util.List;


@Data
public class OptionReviewDetailDTO {

    private Boolean isSelected;
    private Boolean isCorrect;
    private String feedback;
    private List<String> extras;
    private String points;

}
