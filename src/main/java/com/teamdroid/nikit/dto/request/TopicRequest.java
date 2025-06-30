package com.teamdroid.nikit.dto.request;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TopicRequest {
    private String name;
    private String description;
    private String userId;
    private List<QuizRequest> quizzes;
}
