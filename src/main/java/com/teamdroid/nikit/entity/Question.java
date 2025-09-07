package com.teamdroid.nikit.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "question")
public class Question {

    @Id
    private String id;
    private String question;
    private Integer questionVersion;

    // Relations
    private String quizId;
    private Audit audit;

    @Transient
    private List<Option> options;

}
