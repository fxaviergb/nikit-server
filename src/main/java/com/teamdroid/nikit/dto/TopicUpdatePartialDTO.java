package com.teamdroid.nikit.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicUpdatePartialDTO {
    private String name;
    private String description;
}
