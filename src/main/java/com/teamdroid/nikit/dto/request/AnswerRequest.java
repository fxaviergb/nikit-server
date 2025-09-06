package com.teamdroid.nikit.dto.request;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AnswerRequest {
    private Boolean isCorrect;
    private String justification;
    private List<String> extras;
}
