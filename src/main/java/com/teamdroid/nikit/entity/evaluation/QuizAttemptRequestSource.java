package com.teamdroid.nikit.entity.evaluation;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuizAttemptRequestSource {

    private List<String> knowledges = new ArrayList<>();
    private List<String> topics = new ArrayList<>();
    private List<String> quizzes = new ArrayList<>();

}
