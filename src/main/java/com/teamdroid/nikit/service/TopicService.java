package com.teamdroid.nikit.service;

import com.teamdroid.nikit.entity.*;
import com.teamdroid.nikit.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private KnowledgeRepository knowledgeRepository;

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


    public Topic createTopicForKnowledge(String knowledgeId, Topic topic) {
        Knowledge knowledge = knowledgeRepository.findById(knowledgeId).orElseThrow(() -> new RuntimeException("Knowledge not found"));

        for (Questionnaire questionnaire : topic.getQuestionnaires()) {
            for (Question question : questionnaire.getQuestions()) {
                for (Option option : question.getOptions()) {
                    Answer savedAnswer = answerRepository.save(option.getAnswer());
                    option.setAnswerId(savedAnswer.getId());
                    Option savedOption = optionRepository.save(option);
                    question.getOptionIds().add(savedOption.getId());
                }
                Question savedQuestion = questionRepository.save(question);
                questionnaire.getQuestionIds().add(savedQuestion.getId());
            }
            Questionnaire savedQuestionnaire = questionnaireRepository.save(questionnaire);
            topic.getQuestionnaireIds().add(savedQuestionnaire.getId());
        }

        Topic savedTopic = topicRepository.save(topic);
        knowledge.getTopicIds().add(savedTopic.getId());
        knowledgeRepository.save(knowledge);

        return savedTopic;
    }

    public Topic addQuestionnairesToTopic(String topicId, List<Questionnaire> questionnaires) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found"));

        for (Questionnaire questionnaire : questionnaires) {
            for (Question question : questionnaire.getQuestions()) {
                for (Option option : question.getOptions()) {
                    Answer savedAnswer = answerRepository.save(option.getAnswer());
                    option.setAnswerId(savedAnswer.getId());
                    Option savedOption = optionRepository.save(option);
                    question.getOptionIds().add(savedOption.getId());
                }
                Question savedQuestion = questionRepository.save(question);
                questionnaire.getQuestionIds().add(savedQuestion.getId());
            }
            Questionnaire savedQuestionnaire = questionnaireRepository.save(questionnaire);
            topic.getQuestionnaireIds().add(savedQuestionnaire.getId());
        }

        return topicRepository.save(topic);
    }
}
