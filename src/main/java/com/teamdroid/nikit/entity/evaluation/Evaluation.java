package com.teamdroid.nikit.entity.evaluation;

import com.teamdroid.nikit.entity.Audit;
import com.teamdroid.nikit.shared.audit.AuditFactory;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "evaluation")
public class Evaluation {

    @Id
    private String id;
    private QuizAttempt quiz;
    private Audit audit;

    @Transient
    private List<EvaluationAttempt> attempts = new ArrayList<>();

    public Evaluation(QuizAttempt quizAttempt, String userId) {
        this.quiz = quizAttempt;
        this.audit = AuditFactory.create(userId);
    }

    public void addQuizzyAttemptExecution(EvaluationAttempt quizzyAttemptExecution) {
        if (!Objects.isNull(quizzyAttemptExecution)) {
            if (Objects.isNull(this.attempts)) {
                this.attempts = new ArrayList<>();
            }
            if (!this.attempts.contains(quizzyAttemptExecution)) {
                this.attempts.add(quizzyAttemptExecution);
            }
        }
    }

}
