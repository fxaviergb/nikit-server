package com.teamdroid.nikit.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "topic")
public class Topic {

    @Id
    private String id;
    private String name;
    private String description;

    // Relations
    private String knowledgeId;
    private List<String> quizIds;

    private Audit audit;

}
