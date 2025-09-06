package com.teamdroid.nikit.model.view;

import com.teamdroid.nikit.entity.Quiz;
import com.teamdroid.nikit.entity.evaluation.EvaluationAttempt;
import com.teamdroid.nikit.service.model.QuestionService;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuizSummary {
    private Quiz quiz;
    private List<EvaluationAttempt> evaluationAttempts = new ArrayList<>();

    public static QuizSummary build(Quiz quiz, List<EvaluationAttempt> evaluationAttempts) {
        QuizSummary quizSummary = new QuizSummary();
        quizSummary.setQuiz(quiz);
        quizSummary.setEvaluationAttempts(evaluationAttempts);
        return quizSummary;
    }

}
