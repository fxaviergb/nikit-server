package com.teamdroid.nikit.service;

import com.teamdroid.nikit.entity.*;
import com.teamdroid.nikit.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestionnaireService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public Questionnaire createQuestionnaireForTopic(String topicId, Questionnaire questionnaire) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found"));

        for (Question question : questionnaire.getQuestions()) {
            for (Option option : question.getOptions()) {
                Answer savedAnswer = answerRepository.save(option.getAnswer());
                option.setAnswerId(savedAnswer.getId());
                Option savedOption = optionRepository.save(option);
                question.getOptionIds().add(savedOption.getId());
            }
            Question savedQuestion = questionRepository.save(question);
            questionnaire.addQuestionId(savedQuestion.getId());
        }

        Questionnaire savedQuestionnaire = questionnaireRepository.save(questionnaire);
        topic.getQuestionnaireIds().add(savedQuestionnaire.getId());
        topicRepository.save(topic);

        return savedQuestionnaire;
    }

    public Questionnaire addQuestionsToQuestionnaire(String questionnaireId, List<Question> questions) {
        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireId).orElseThrow(() -> new RuntimeException("Questionnaire not found"));

        for (Question question : questions) {
            for (Option option : question.getOptions()) {
                Answer savedAnswer = answerRepository.save(option.getAnswer());
                option.setAnswerId(savedAnswer.getId());
                Option savedOption = optionRepository.save(option);
                question.getOptionIds().add(savedOption.getId());
            }
            Question savedQuestion = questionRepository.save(question);
            questionnaire.addQuestion(savedQuestion);
        }

        return questionnaireRepository.save(questionnaire);
    }
}
