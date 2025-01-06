package com.teamdroid.nikit.service;

import com.teamdroid.nikit.entity.*;
import com.teamdroid.nikit.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KnowledgeService {

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AuditRepository auditRepository;


    public List<Knowledge> getAllKnowledge() {
        return knowledgeRepository.findAll();
    }

    public Optional<Knowledge> getKnowledgeById(String id) {
        return knowledgeRepository.findById(id);
    }

    public Knowledge createKnowledge(Knowledge knowledge) {
        return knowledgeRepository.save(knowledge);
    }

    public Knowledge updateKnowledge(String id, Knowledge knowledge) {
        knowledge.setId(id);
        return knowledgeRepository.save(knowledge);
    }

    public void deleteKnowledge(String id) {
        knowledgeRepository.deleteById(id);
    }

    @Transactional
    public Knowledge createFullKnowledge(Knowledge knowledge) {
        for (Topic topic : knowledge.getTopics()) {
            for (Quiz quiz : topic.getQuizzes()) {
                for (Question question : quiz.getQuestions()) {
                    for (Option option : question.getOptions()) {
                        Answer savedAnswer = answerRepository.save(option.getAnswer());
                        option.setAnswerId(savedAnswer.getId());
                        Option savedOption = optionRepository.save(option);
                        question.getOptionIds().add(savedOption.getId());
                    }
                    Question savedQuestion = questionRepository.save(question);
                    quiz.getQuestionIds().add(savedQuestion.getId());
                }
                Quiz savedQuiz = quizRepository.save(quiz);
                topic.getQuizIds().add(savedQuiz.getId());
            }
            Topic savedTopic = topicRepository.save(topic);
            knowledge.getTopicIds().add(savedTopic.getId());
        }
        return knowledgeRepository.save(knowledge);
    }

    @Transactional
    public Knowledge addTopicsToKnowledge(String knowledgeId, List<Topic> topics) {
        Knowledge knowledge = knowledgeRepository.findById(knowledgeId).orElseThrow(
                () -> new RuntimeException("Knowledge not found"));
        for (Topic topic : topics) {
            for (Quiz quiz : topic.getQuizzes()) {
                for (Question question : quiz.getQuestions()) {
                    for (Option option : question.getOptions()) {
                        Answer savedAnswer = answerRepository.save(option.getAnswer());
                        option.setAnswerId(savedAnswer.getId());
                        Option savedOption = optionRepository.save(option);
                        question.getOptionIds().add(savedOption.getId());
                    }
                    Question savedQuestion = questionRepository.save(question);
                    quiz.getQuestionIds().add(savedQuestion.getId());
                }
                Quiz savedQuiz = quizRepository.save(quiz);
                topic.getQuizIds().add(savedQuiz.getId());
            }
            Topic savedTopic = topicRepository.save(topic);
            knowledge.getTopicIds().add(savedTopic.getId());
            if (knowledge.getTopics() == null) {
                knowledge.setTopics(new ArrayList<>());
            }
            knowledge.getTopics().add(topic);
        }
        return knowledgeRepository.save(knowledge);
    }
}
