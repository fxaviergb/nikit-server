package com.teamdroid.nikit.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "quiz")
public class Quiz {

    @Id
    private String id;
    private String name;
    private String description;
    private List<String> questionIds = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();

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

    public void addQuestions(List<Question> questions) {
        if (!Objects.isNull(questions)) {
            if (Objects.isNull(this.questions)) {
                this.questions = new ArrayList<>();
            }
            questions.forEach(q -> {
                if (!this.questions.contains(q)) {
                    this.questions.add(q);
                    addQuestionId(q.getId());
                }
            });
        }
    }

    public void initializeQuestions(List<Question> questions) {
        this.questions.clear();
        this.questionIds.clear();
        addQuestions(questions);
    }
}
