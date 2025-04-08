package com.teamdroid.nikit.mapper;

import com.teamdroid.nikit.dto.*;
import com.teamdroid.nikit.entity.evaluation.EvaluationAttempt;
import com.teamdroid.nikit.entity.evaluation.Grade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

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
        return dto;
    }

    default EvaluationAttemptReviewDTO toDetailedReviewDto(EvaluationAttempt attempt) {
        if (attempt == null) return null;

        EvaluationAttemptReviewDTO dto = new EvaluationAttemptReviewDTO();
        dto.setAttemptId(attempt.getId());
        dto.setQuizId(attempt.getQuiz().getIdBase());

        // Mapeo de calificación general
        Grade grade = attempt.getGrade();
        if (grade != null) {
            EvaluationAttemptReviewGradeDTO gradeDTO = new EvaluationAttemptReviewGradeDTO();
            gradeDTO.setQualification(String.valueOf(grade.getScore()));
            gradeDTO.setMaxQualification(String.valueOf(grade.getMaximumScore()));
            gradeDTO.setReviewDate(grade.getCreatedDate());
            dto.setGrade(gradeDTO);
        }

        // Mapeo de preguntas
        List<QuestionReviewDTO> questions = attempt.getQuiz().getQuestions().stream().map(question -> {
            QuestionReviewDTO questionDTO = new QuestionReviewDTO();
            questionDTO.setId(question.getId());
            questionDTO.setQuestion(question.getQuestion());

            // Opciones
            List<OptionReviewDTO> options = question.getOptions().stream().map(option -> {
                OptionReviewDTO optionDTO = new OptionReviewDTO();
                optionDTO.setId(option.getId());
                optionDTO.setOption(option.getOption());

                OptionReviewDetailDTO detail = new OptionReviewDetailDTO();
                detail.setIsSelected(option.getIsSelected());
                detail.setIsCorrect(option.getAnswer() != null && option.getAnswer().getIsCorrect());
                detail.setFeedback(option.getAnswer() != null ? option.getAnswer().getJustification() : "");
                detail.setPoints("1"); // O calcula si es dinámico
                optionDTO.setReview(detail);

                return optionDTO;
            }).toList();

            questionDTO.setOptions(options);

            // Resumen de pregunta
            QuestionReviewSummaryDTO summary = new QuestionReviewSummaryDTO();
            summary.setStatus(question.getGrade() != null && question.getGrade().getScore() > 0 ? "CORRECT" : "INCORRECT");
            summary.setFeedback(options.stream()
                    .map(o -> o.getReview().getFeedback())
                    .filter(f -> f != null && !f.isEmpty())
                    .findFirst()
                    .orElse(""));
            summary.setPoints("1"); // O de question.getGrade().getScore()
            questionDTO.setReview(summary);

            return questionDTO;
        }).toList();

        dto.setReview(questions);

        return dto;
    }
}
