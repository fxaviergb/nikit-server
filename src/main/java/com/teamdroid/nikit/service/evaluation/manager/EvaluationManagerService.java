package com.teamdroid.nikit.service.evaluation.manager;

import com.teamdroid.nikit.dto.OptionAttemptRegisterDTO;
import com.teamdroid.nikit.dto.QuestionAttemptRegisterDTO;
import com.teamdroid.nikit.dto.EvaluationAttemptRegisterDTO;
import com.teamdroid.nikit.entity.evaluation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class EvaluationManagerService {


    public void review(EvaluationAttempt quizzyAttempt, EvaluationAttemptRegisterDTO quizzyAttemptRegisterDTO) {
        List<QuestionAttempt> questions = quizzyAttempt.getQuiz().getQuestions();
        List<QuestionAttemptRegisterDTO> questionDTOs = quizzyAttemptRegisterDTO.getQuestions();

        quizzyAttempt.setExecutionDate(quizzyAttemptRegisterDTO.getExecutionDate());

        registerQuestions(questions, questionDTOs);
        evaluate(quizzyAttempt);
    }

    private void registerQuestions(List<QuestionAttempt> questionAttempts, List<QuestionAttemptRegisterDTO> questionDTOs) {
        Map<String, QuestionAttempt> questionMap = questionAttempts.stream()
                .collect(Collectors.toMap(QuestionAttempt::getId, q -> q));

        for (QuestionAttemptRegisterDTO qDTO : questionDTOs) {
            QuestionAttempt question = questionMap.get(qDTO.getId());
            if (question == null) {
                throw new RuntimeException("The question id " + qDTO.getId() + " does not exist");
            }
            registerOptions(question.getOptions(), qDTO.getOptions());
        }
    }

    private void registerOptions(List<OptionAttempt> optionAttempts, List<OptionAttemptRegisterDTO> optionDTOs) {
        Map<String, OptionAttempt> optionMap = optionAttempts.stream()
                .collect(Collectors.toMap(OptionAttempt::getId, o -> o));

        for (OptionAttemptRegisterDTO oDTO : optionDTOs) {
            OptionAttempt option = optionMap.get(oDTO.getId());
            if (option == null) {
                throw new RuntimeException("The option id " + oDTO.getId() + " does not exist");
            }
            option.setSelected(oDTO.isSelected());
        }
    }

    public void evaluate(EvaluationAttempt quizzyAttempt) {
        double totalScore = 0;
        List<QuestionAttempt> questions = quizzyAttempt.getQuiz().getQuestions();

        for (QuestionAttempt question : questions) {
            double questionScore = calculateScore(question);
            question.setGrade(Grade.build(questionScore, 1d));
            totalScore += questionScore;
        }

        quizzyAttempt.setGrade(Grade.build(totalScore, questions.size()));
    }

    private double calculateScore(QuestionAttempt question) {
        List<OptionAttempt> selectedOptions = question.getOptions().stream()
                .filter(OptionAttempt::isSelected)
                .toList();

        if (selectedOptions.size() == 1 && selectedOptions.get(0).getAnswer().isCorrect()) {
            return 1;
        }
        return 0;
    }
}
