package com.teamdroid.nikit.entity.evaluation;

import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private double efficiencyPercentage;
    private LocalDateTime createdDate;

    public static Grade build(double score, double maximumScore) {
        Grade grade = new Grade();
        grade.setCreatedDate(LocalDateTime.now());
        grade.setMinimumScore(0d);
        grade.setMaximumScore(maximumScore);
        grade.setScore(score);

        // Cálculo del porcentaje de eficiencia redondeado a 2 decimales
        double efficiency = (maximumScore > 0)
                ? BigDecimal.valueOf((score / maximumScore) * 100)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue()
                : 0.00;

        grade.setEfficiencyPercentage(efficiency);

        return grade;
    }
}
