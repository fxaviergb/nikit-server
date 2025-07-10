package com.teamdroid.nikit.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class QuestionAttemptRegisterDTO {

    private String id;
    private List<OptionAttemptRegisterDTO> options = new ArrayList<>();

}
