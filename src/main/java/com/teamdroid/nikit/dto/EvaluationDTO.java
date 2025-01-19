package com.teamdroid.nikit.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class EvaluationDTO {

    private String id;
    private List<EvaluationAttemptDTO> attempts = new ArrayList<>();

}
