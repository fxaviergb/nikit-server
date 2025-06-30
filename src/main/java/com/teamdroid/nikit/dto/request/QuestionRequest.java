package com.teamdroid.nikit.dto.request;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionRequest {
    private String question;
    private List<OptionRequest> options;
}
