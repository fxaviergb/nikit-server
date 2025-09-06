package com.teamdroid.nikit.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "quiz")
public class Quiz {

    @Id
    private String id;
    private String name;
    private String description;
    private Integer version;

    // Relations
    private List<String> topicIds;
    private String userId;

    private Audit audit;

    @Transient
    private List<Question> questions;
}
