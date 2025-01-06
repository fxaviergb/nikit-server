package com.teamdroid.nikit.service;

import com.teamdroid.nikit.entity.*;
import com.teamdroid.nikit.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

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


    public Topic createTopicForKnowledge(String knowledgeId, Topic topic) {
        Knowledge knowledge = knowledgeRepository.findById(knowledgeId).orElseThrow(() -> new RuntimeException("Knowledge not found"));

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
        knowledgeRepository.save(knowledge);

        return savedTopic;
    }

    public Topic addQuizzesToTopic(String topicId, List<Quiz> quizzes) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found"));

        for (Quiz quiz : quizzes) {
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

        return topicRepository.save(topic);
    }
}
