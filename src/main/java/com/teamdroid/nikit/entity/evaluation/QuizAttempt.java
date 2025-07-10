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
@Document(collection = "quiz_attempt")
public class QuizAttempt {

    @Id
    private String id;
    private String idBase;
    private List<QuestionAttempt> questions = new ArrayList<>();

}
