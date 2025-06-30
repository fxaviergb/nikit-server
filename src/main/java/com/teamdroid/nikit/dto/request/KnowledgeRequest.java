package com.teamdroid.nikit.dto.request;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class KnowledgeRequest {
    private String name;
    private String description;
    private List<TopicRequest> topics;
}
