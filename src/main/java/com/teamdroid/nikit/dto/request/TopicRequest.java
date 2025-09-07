package com.teamdroid.nikit.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TopicRequest {
    @NotBlank(message = "El nombre del tópico no puede estar vacío")
    @Size(max = 150, message = "El nombre del tópico no debe exceder los 150 caracteres")
    private String name;
    private String description;
    private List<QuizRequest> quizzes;
}
