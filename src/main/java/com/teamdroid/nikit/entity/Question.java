package com.teamdroid.nikit.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
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
    private List<String> optionIds = new ArrayList<>();

    private List<Option> options;

}
