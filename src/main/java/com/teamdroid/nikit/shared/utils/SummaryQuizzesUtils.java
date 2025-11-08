package com.teamdroid.nikit.shared.utils;

import com.teamdroid.nikit.dto.QuizSummaryAttemptDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class SummaryQuizzesUtils {

    public static Double calculateEfficiencyPercentage(List<QuizSummaryAttemptDTO> attempts) {
        double avgEfficiency = attempts.stream()
                .mapToDouble(dto -> dto.getEfficiencyPercentage() != null ? dto.getEfficiencyPercentage() : 0.0)
                .average()
                .orElse(0.0);
        BigDecimal rounded = BigDecimal.valueOf(avgEfficiency).setScale(2, RoundingMode.HALF_UP);
        return rounded.doubleValue();
    }

}
