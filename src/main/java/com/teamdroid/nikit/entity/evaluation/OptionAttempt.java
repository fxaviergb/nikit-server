package com.teamdroid.nikit.entity.evaluation;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OptionAttempt {

    private String id;
    private String option;
    private AnswerAttempt answer;
    private Boolean isSelected;

}
