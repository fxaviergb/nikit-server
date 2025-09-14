package com.teamdroid.nikit.dto.request;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionRequest {
    private String id;
    private String question;
    private Integer questionVersion;
    private List<OptionRequest> options;
}
