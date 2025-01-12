package com.teamdroid.nikit.entity.execution;

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
@Document(collection = "question_attempt")
public class QuestionAttempt {

    @Id
    private String id;
    private String question;
    private List<String> optionIds = new ArrayList<>();
    private List<OptionAttempt> options = new ArrayList<>();

    public void addOptionId(String optionId) {
        if (!Objects.isNull(optionId)) {
            if (Objects.isNull(this.optionIds)) {
                this.optionIds = new ArrayList<>();
            }
            if (!this.optionIds.contains(optionId)) {
                this.optionIds.add(optionId);
            }
        }
    }

    public void addOptions(List<OptionAttempt> options) {
        if (!Objects.isNull(options)) {
            if (Objects.isNull(this.options)) {
                this.options = new ArrayList<>();
            }
            options.forEach(o -> {
                if (!this.options.contains(o)) {
                    this.options.add(o);
                    addOptionId(o.getId());
                }
            });
        }
    }

    public void initializeOptions(List<OptionAttempt> options) {
        this.options.clear();
        this.optionIds.clear();
        addOptions(options);
    }

}
