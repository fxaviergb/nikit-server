package com.teamdroid.nikit.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class QuestionAttemptDTO {

    private String id;
    private String question;
    private List<OptionAttemptDTO> options = new ArrayList<>();

}
