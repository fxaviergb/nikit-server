package com.teamdroid.nikit.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicDTO {
    private String id;
    private String name;
    private String description;
}
