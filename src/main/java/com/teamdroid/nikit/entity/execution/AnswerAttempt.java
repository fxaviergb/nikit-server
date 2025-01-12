package com.teamdroid.nikit.entity.execution;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "answer_attempt")
public class AnswerAttempt {

    @Id
    private String id;
    private boolean isCorrect;
    private String justification;
    private List<String> extras;

}
