package com.teamdroid.nikit.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicCreateDTO {
    @NotBlank(message = "El nombre del tópico no puede estar vacío")
    @Size(max = 150, message = "El nombre del tópico no debe exceder los 150 caracteres")
    private String name;
    private String description;
}
