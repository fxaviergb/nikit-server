package com.teamdroid.nikit.dto;

import lombok.Data;


@Data
public class OptionReviewDetailDTO {

    private Boolean isSelected;
    private Boolean isCorrect;
    private String feedback;
    private String points;

}
