package com.teamdroid.nikit.dto.request;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptionRequest {
    private String id;
    private String option;
    private AnswerRequest answer;
}
