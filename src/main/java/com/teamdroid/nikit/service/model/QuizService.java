package com.teamdroid.nikit.service.model;

import com.teamdroid.nikit.entity.*;
import com.teamdroid.nikit.entity.evaluation.EvaluationAttempt;
import com.teamdroid.nikit.model.view.QuizSummary;
import com.teamdroid.nikit.repository.model.QuizRepository;
import com.teamdroid.nikit.service.evaluation.EvaluationAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private EvaluationAttemptService evaluationAttemptService;

    public Quiz findById(String quizId) {
        return quizRepository.findById(quizId).orElseThrow(
                () -> new RuntimeException("Quiz not found"));
    }

    public Quiz create(Quiz quiz) {
        Assert.notNull(quiz, "The quiz cannot be null");

        List<Question> questions = questionService.create(quiz.getQuestions());
        quiz.initializeQuestions(questions);
        return quizRepository.save(quiz);
    }

    public List<Quiz> create(List<Quiz> quizzes) {
        Assert.notNull(quizzes, "The list of quizzes cannot be null");

        return quizzes.stream()
                .map(this::create)
                .collect(Collectors.toList());
    }

    public Quiz createFullForTopic(String topicId, Quiz quiz) {
        Assert.notNull(topicId, "The topic Id cannot be null");
        Assert.notNull(quiz, "The quiz cannot be null");

        Topic topic = topicService.findById(topicId);
        Quiz createdQuiz = create(quiz);
        topicService.addPersistentQuizzes(topic, createdQuiz);
        return createdQuiz;
    }

    public Quiz addTransientQuestions(String quizId, List<Question> questions) {
        Quiz quiz = findById(quizId);
        List<Question> createdQuestions = questionService.create(questions);
        quiz.addQuestions(createdQuestions);
        return quizRepository.save(quiz);
    }

    public QuizSummary findSummaryById(String quizId) {
        Quiz quiz = findById(quizId);
        List<EvaluationAttempt> evaluationAttempts = evaluationAttemptService.getByQuizIdBase(quizId);
        return QuizSummary.build(quiz, evaluationAttempts);
    }
}
