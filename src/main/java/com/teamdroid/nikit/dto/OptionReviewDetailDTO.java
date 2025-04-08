package com.teamdroid.nikit.dto;

import lombok.Data;


@Data
public class OptionReviewDetailDTO {

    private boolean isSelected;
    private boolean isCorrect;
    private String feedback;
    private String points;

}
