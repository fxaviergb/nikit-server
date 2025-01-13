package com.teamdroid.nikit.entity.execution;

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
    private boolean isSelected;

}
