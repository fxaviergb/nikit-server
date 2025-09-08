package com.teamdroid.nikit.entity.evaluation;

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
public class QuizAttempt {

    private String id;
    private String name;
    private String description;
    private Integer version;
    private List<QuestionAttempt> questions = new ArrayList<>();

}
