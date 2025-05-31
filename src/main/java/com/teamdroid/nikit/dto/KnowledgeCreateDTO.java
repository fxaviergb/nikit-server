package com.teamdroid.nikit.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KnowledgeCreateDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
}
