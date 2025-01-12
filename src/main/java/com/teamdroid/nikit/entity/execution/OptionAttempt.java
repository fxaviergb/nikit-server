package com.teamdroid.nikit.entity.execution;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "option_attempt")
public class OptionAttempt {

    @Id
    private String id;
    private String option;
    private String answerId;
    private AnswerAttempt answer;

    public void initializeAnswer(AnswerAttempt answer) {
        this.answer = answer;
        this.answerId = answer.getId();
    }

}
