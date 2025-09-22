package com.teamdroid.nikit.mapper;

import com.teamdroid.nikit.dto.*;
import com.teamdroid.nikit.entity.evaluation.EvaluationAttempt;
import com.teamdroid.nikit.entity.evaluation.Grade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EvaluationAttemptMapper {

    @Mapping(source = "id", target = "attemptId")
    @Mapping(source = "quiz.id", target = "quizId")
    @Mapping(source = "grade", target = "grade", qualifiedByName = "mapGrade")
    EvaluationAttemptReviewSummaryDTO toEvaluationAttemptReviewSummaryDTO(EvaluationAttempt attempt);

    @Named("mapGrade")
    default EvaluationAttemptReviewSummaryGradeDTO mapGrade(Grade grade) {
        if (grade == null) return null;

        EvaluationAttemptReviewSummaryGradeDTO dto = new EvaluationAttemptReviewSummaryGradeDTO();
        dto.setQualification(grade.getScore());
        dto.setMaxQualification(grade.getMaximumScore());
        dto.setReviewDate(grade.getCreatedDate());
        dto.setEfficiencyPercentage(grade.getEfficiencyPercentage());
        return dto;
    }

    default EvaluationAttemptReviewDTO toDetailedReviewDto(EvaluationAttempt attempt) {
        if (attempt == null) return null;

        EvaluationAttemptReviewDTO dto = new EvaluationAttemptReviewDTO();
        dto.setAttemptId(attempt.getId());
        dto.setQuizId(attempt.getQuiz().getId());
        dto.setQuizType(attempt.getQuiz().getType());
        dto.setRequestSource(attempt.getQuiz().getRequestSource());

        // Calificación general
        Optional.ofNullable(attempt.getGrade()).ifPresent(grade -> {
            EvaluationAttemptReviewGradeDTO gradeDTO = new EvaluationAttemptReviewGradeDTO();
            gradeDTO.setQualification(String.valueOf(grade.getScore()));
            gradeDTO.setMaxQualification(String.valueOf(grade.getMaximumScore()));
            gradeDTO.setReviewDate(grade.getCreatedDate());
            gradeDTO.setEfficiencyPercentage(grade.getEfficiencyPercentage());
            dto.setGrade(gradeDTO);
        });

        // Mapeo de preguntas
        List<QuestionReviewDTO> questions = attempt.getQuiz().getQuestions().stream().map(question -> {
            QuestionReviewDTO questionDTO = new QuestionReviewDTO();
            questionDTO.setId(question.getId());
            questionDTO.setQuestion(question.getQuestion());

            // Mapeo de opciones
            List<OptionReviewDTO> options = question.getOptions().stream().map(option -> {
                OptionReviewDTO optionDTO = new OptionReviewDTO();
                optionDTO.setId(option.getId());
                optionDTO.setOption(option.getOption());

                var answer = option.getAnswer();
                OptionReviewDetailDTO detail = new OptionReviewDetailDTO();
                detail.setIsSelected(option.getIsSelected());
                detail.setIsCorrect(answer != null && Boolean.TRUE.equals(answer.getIsCorrect()));
                detail.setFeedback(answer != null ? answer.getJustification() : "");
                detail.setExtras(answer != null ? answer.getExtras() : null);
                detail.setPoints("1"); // Personaliza si es dinámico
                optionDTO.setReview(detail);

                return optionDTO;
            }).toList();

            questionDTO.setOptions(options);

            // Opción correcta (si existe)
            OptionReviewDTO correctOption = options.stream()
                    .filter(o -> o.getReview() != null && Boolean.TRUE.equals(o.getReview().getIsCorrect()))
                    .findFirst()
                    .orElse(null);

            // Resumen de la pregunta
            Grade questionGrade = question.getGrade();
            QuestionReviewSummaryDTO summary = new QuestionReviewSummaryDTO();
            summary.setStatus(questionGrade != null && questionGrade.getScore() > 0 ? "CORRECT" : "INCORRECT");
            summary.setFeedback(
                    Optional.ofNullable(correctOption)
                            .map(OptionReviewDTO::getReview)
                            .map(OptionReviewDetailDTO::getFeedback)
                            .filter(f -> !f.isEmpty())
                            .orElse("")
            );
            summary.setExtras(
                    Optional.ofNullable(correctOption)
                            .map(OptionReviewDTO::getReview)
                            .map(OptionReviewDetailDTO::getExtras)
                            .orElse(Collections.emptyList())
            );
            summary.setPoints("1"); // Personaliza si quieres usar questionGrade.getScore()
            questionDTO.setReview(summary);

            return questionDTO;
        }).toList();

        dto.setReview(questions);
        return dto;
    }


}
