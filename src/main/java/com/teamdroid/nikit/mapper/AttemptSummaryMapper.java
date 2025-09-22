package com.teamdroid.nikit.mapper;

import com.teamdroid.nikit.dto.AttemptSummaryDTO;
import com.teamdroid.nikit.dto.QuizSummaryAttemptDTO;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AttemptSummaryMapper {

    default AttemptSummaryDTO toSummary(List<QuizSummaryAttemptDTO> attempts) {
        AttemptSummaryDTO summary = new AttemptSummaryDTO();
        summary.setAttempts(attempts);

        double avgEfficiency = attempts.stream()
                .mapToDouble(dto -> dto.getEfficiencyPercentage() != null ? dto.getEfficiencyPercentage() : 0.0)
                .average()
                .orElse(0.0);
        BigDecimal rounded = BigDecimal.valueOf(avgEfficiency).setScale(2, RoundingMode.HALF_UP);
        summary.setEfficiencyPercentage(rounded.doubleValue());

        return summary;
    }

}
