package com.teamdroid.nikit.mapper;

import com.teamdroid.nikit.dto.*;
import com.teamdroid.nikit.entity.Quiz;
import com.teamdroid.nikit.entity.evaluation.EvaluationAttempt;
import com.teamdroid.nikit.model.view.QuizSummary;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuizSummaryMapper {

    default QuizSummaryDTO toQuizSummaryDTO(QuizSummary quizSummary) {
        if (quizSummary == null) {
            return null;
        }

        Quiz quiz = quizSummary.getQuiz();
        List<EvaluationAttempt> evaluationAttempts = quizSummary.getEvaluationAttempts();

        QuizSummaryDTO dto = new QuizSummaryDTO();
        dto.setId(quiz.getId());
        dto.setName(quiz.getName());
        dto.setDescription(quiz.getDescription());

        dto.setMetadata(buildMetadata(quiz));
        dto.setAttempts(buildAttemptDTOs(evaluationAttempts));

        return dto;
    }

    private QuizSummaryMetadataDTO buildMetadata(Quiz quiz) {
        QuizSummaryMetadataDTO metadata = new QuizSummaryMetadataDTO();
        metadata.setQuestions(Optional.ofNullable(quiz.getQuestions()).map(List::size).orElse(0));
        return metadata;
    }

    private List<QuizSummaryAttemptDTO> buildAttemptDTOs(List<EvaluationAttempt> attempts) {
        return Optional.ofNullable(attempts)
                .orElse(List.of())
                .stream()
                .map(this::buildAttemptDTO)
                .collect(Collectors.toList());
    }

    private QuizSummaryAttemptDTO buildAttemptDTO(EvaluationAttempt attempt) {
        QuizSummaryAttemptDTO dto = new QuizSummaryAttemptDTO();
        dto.setId(attempt.getId());

        Optional.ofNullable(attempt.getGrade()).ifPresent(grade -> {
            dto.setGrade(grade.getScore());
            dto.setMaxGrade(grade.getMaximumScore());
            dto.setDate(grade.getCreatedDate());
            dto.setEfficiencyPercentage(grade.getEfficiencyPercentage());
        });

        return dto;
    }
}
