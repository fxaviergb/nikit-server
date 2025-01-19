package com.teamdroid.nikit.entity.execution;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionAttempt {

    private String id;
    private String question;
    private List<OptionAttempt> options = new ArrayList<>();

    private Grade grade;

}
