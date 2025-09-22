package com.teamdroid.nikit.mapper;

import com.teamdroid.nikit.dto.QuizSummaryAttemptDTO;
import com.teamdroid.nikit.entity.evaluation.EvaluationAttempt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuizSummaryAttemptMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "grade.score", target = "grade")
    @Mapping(source = "grade.maximumScore", target = "maxGrade")
    @Mapping(source = "grade.efficiencyPercentage", target = "efficiencyPercentage")
    @Mapping(source = "grade.createdDate", target = "date")
    QuizSummaryAttemptDTO toDTO(EvaluationAttempt attempt);
}
