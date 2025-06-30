package com.teamdroid.nikit.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    private String userId;
    private String knowledgeId;
    private List<String> quizIds;

    private Audit audit;

}
