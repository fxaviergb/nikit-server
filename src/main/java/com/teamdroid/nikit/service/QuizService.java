package com.teamdroid.nikit.service;

import com.teamdroid.nikit.entity.*;
import com.teamdroid.nikit.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuizService {

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

    public Quiz createQuizForTopic(String topicId, Quiz quiz) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found"));

        for (Question question : quiz.getQuestions()) {
            for (Option option : question.getOptions()) {
                Answer savedAnswer = answerRepository.save(option.getAnswer());
                option.setAnswerId(savedAnswer.getId());
                Option savedOption = optionRepository.save(option);
                question.getOptionIds().add(savedOption.getId());
            }
            Question savedQuestion = questionRepository.save(question);
            quiz.addQuestionId(savedQuestion.getId());
        }

        Quiz savedQuiz = quizRepository.save(quiz);
        topic.getQuizIds().add(savedQuiz.getId());
        topicRepository.save(topic);

        return savedQuiz;
    }

    public Quiz addQuestionsToQuiz(String quizId, List<Question> questions) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));

        for (Question question : questions) {
            for (Option option : question.getOptions()) {
                Answer savedAnswer = answerRepository.save(option.getAnswer());
                option.setAnswerId(savedAnswer.getId());
                Option savedOption = optionRepository.save(option);
                question.getOptionIds().add(savedOption.getId());
            }
            Question savedQuestion = questionRepository.save(question);
            quiz.addQuestion(savedQuestion);
        }

        return quizRepository.save(quiz);
    }
}
