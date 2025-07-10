package com.teamdroid.nikit.dto;

import lombok.Data;


@Data
public class OptionReviewDTO {

    private String id;
    private String option;
    private OptionReviewDetailDTO review;

}
