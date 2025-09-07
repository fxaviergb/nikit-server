package com.teamdroid.nikit.entity.evaluation;

import com.teamdroid.nikit.entity.Audit;
import com.teamdroid.nikit.shared.audit.AuditFactory;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "evaluation_attempt")
public class EvaluationAttempt {

    @Id
    private String id;
    private QuizAttempt quiz;
    private LocalDateTime executionDate;
    private String evaluationId;
    private Audit audit;

    private Grade grade;

    public EvaluationAttempt(Evaluation evaluation, QuizAttempt quiz) {
        this.evaluationId = evaluation.getId();
        this.quiz = quiz;
        this.audit = evaluation.getAudit();
    }

}
