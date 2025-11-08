package com.teamdroid.nikit.mapper;

import com.teamdroid.nikit.dto.AttemptSummaryDTO;
import com.teamdroid.nikit.dto.QuizSummaryAttemptDTO;
import com.teamdroid.nikit.shared.utils.SummaryQuizzesUtils;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AttemptSummaryMapper {

    default AttemptSummaryDTO toSummary(List<QuizSummaryAttemptDTO> attempts) {
        AttemptSummaryDTO summary = new AttemptSummaryDTO();
        summary.setAttempts(attempts);
        summary.setEfficiencyPercentage(SummaryQuizzesUtils.calculateEfficiencyPercentage(attempts));
        return summary;
    }

}
