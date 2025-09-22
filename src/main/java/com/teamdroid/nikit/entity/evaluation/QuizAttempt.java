package com.teamdroid.nikit.entity.evaluation;

import com.teamdroid.nikit.model.enumeration.QuizAttemptType;
import lombok.*;

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

    private QuizAttemptType type;
    private List<QuestionAttempt> questions = new ArrayList<>();

    private List<String> sourceQuizIds = new ArrayList<>();
    private QuizAttemptRequestSource requestSource;
    private QuizAttemptRequestParams requestParams;

}
