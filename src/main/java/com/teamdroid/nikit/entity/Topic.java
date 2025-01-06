package com.teamdroid.nikit.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "topic")
public class Topic {

    @Id
    private String id;
    private String name;
    private List<String> quizIds = new ArrayList<>();
    private List<Quiz> quizzes = new ArrayList<>();

    public void addQuizId(String quizId) {
        if (!Objects.isNull(quizId)) {
            if (Objects.isNull(this.quizIds)) {
                this.quizIds = new ArrayList<>();
            }
            if (!this.quizIds.contains(quizId)) {
                this.quizIds.add(quizId);
            }
        }
    }

    public void addQuizzes(List<Quiz> quizzes) {
        if (!Objects.isNull(quizzes)) {
            if (Objects.isNull(this.quizzes)) {
                this.quizzes = new ArrayList<>();
            }
            quizzes.forEach(q -> {
                if (!this.quizzes.contains(q)) {
                    this.quizzes.add(q);
                    addQuizId(q.getId());
                }
            });
        }
    }

    public void addQuizzes(Quiz... quizzes) {
        addQuizzes(Arrays.asList(quizzes));
    }

    public void initializeQuizzes(List<Quiz> quizzes) {
        this.quizzes.clear();
        this.quizIds.clear();
        addQuizzes(quizzes);
    }

}
