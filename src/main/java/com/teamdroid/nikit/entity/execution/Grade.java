package com.teamdroid.nikit.entity.execution;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Grade {

    private double maximumScore;
    private double minimumScore;
    private double score;
    private LocalDateTime createdDate;

    public static Grade build(double score, double maximumScore) {
        Grade grade = new Grade();
        grade.setCreatedDate(LocalDateTime.now());
        grade.setMinimumScore(0d);
        grade.setMaximumScore(maximumScore);
        grade.setScore(score);
        return grade;
    }

}
