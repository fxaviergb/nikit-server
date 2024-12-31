package com.teamdroid.nikit.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "questionnaire")
public class Questionnaire {

    @Id
    private String id;
    private List<String> questionIds = new ArrayList<>();

    private List<Question> questions;

    public void addQuestionId(String questionId) {
        if (!Objects.isNull(questionId)) {
            if (Objects.isNull(this.questionIds)) {
                this.questionIds = new ArrayList<>();
            }
            if (!this.questionIds.contains(questionId)) {
                this.questionIds.add(questionId);
            }
        }
    }

    public void addQuestion(Question question) {
        if (!Objects.isNull(question)) {
            if (Objects.isNull(this.questions)) {
                this.questions = new ArrayList<>();
            }
            if (!this.questions.contains(question)) {
                this.questions.add(question);
                addQuestionId(question.getId());
            }
        }
    }

}
