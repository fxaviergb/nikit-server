package com.teamdroid.nikit.dto.request;
import com.teamdroid.nikit.entity.Answer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptionRequest {
    private String option;
    private AnswerRequest answer;
}
