package com.teamdroid.nikit.service.evaluation;

import com.teamdroid.nikit.entity.Question;
import com.teamdroid.nikit.entity.Quiz;
import com.teamdroid.nikit.entity.Topic;
import com.teamdroid.nikit.entity.evaluation.QuizAttempt;
import com.teamdroid.nikit.entity.evaluation.QuizAttemptRequestParams;
import com.teamdroid.nikit.entity.evaluation.QuizAttemptRequestSource;
import com.teamdroid.nikit.mapper.QuizAttemptFromQuizMapper;
import com.teamdroid.nikit.model.enumeration.QuizAttemptType;
import com.teamdroid.nikit.service.model.QuestionService;
import com.teamdroid.nikit.service.model.QuizService;
import com.teamdroid.nikit.service.model.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
public class QuizAttemptService {

    private final QuizAttemptFromQuizMapper quizAttemptFromQuizMapper;

    @Autowired
    private QuizService quizService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private QuestionService questionService;


    public QuizAttempt createFromQuizBase(String quizId, Integer questionCount) {
        Assert.notNull(quizId, "There is not found a Quiz with the specified Id");
        Quiz quiz = quizService.getForEvaluation(quizId, questionCount);
        QuizAttempt attempt = quizAttemptFromQuizMapper.from(quiz);
        attempt.setType(QuizAttemptType.SINGLED);
        attempt.setSourceQuizIds(List.of(quizId));
        return attempt;
    }

    public QuizAttempt createMixedFromSources(QuizAttemptRequestSource source, QuizAttemptRequestParams params) {
        Set<String> allQuizIds = findQuizIdsFromAttemptSources(
                source.getKnowledges(),
                source.getTopics(),
                source.getQuizzes()
        );
        List<Question> selectedQuestions = questionService.getRandomQuestionsByQuizIds(
                allQuizIds,
                params.getQuestionCount()
        );
        if (selectedQuestions.isEmpty()) {
            throw new IllegalArgumentException("No hay preguntas disponibles para la evaluación.");
        }
        return createFromQuestions(selectedQuestions, source, params);
    }


    public QuizAttempt createFromQuestions(
            List<Question> questions,
            QuizAttemptRequestSource source,
            QuizAttemptRequestParams params
    ) {
        Assert.notEmpty(questions, "No se pueden crear intentos sin preguntas");

        Quiz quiz = new Quiz();
        quiz.setId("mixed-" + System.currentTimeMillis());
        quiz.setName("Cuestionario mezclado entre múltiples fuentes");
        quiz.setDescription("Generado aleatoriamente a partir de múltiples fuentes");
        quiz.setQuestions(questions);

        QuizAttempt attempt = quizAttemptFromQuizMapper.from(quiz);
        attempt.setType(QuizAttemptType.MIXED);

        Set<String> sourceQuizIds = questions.stream()
                .map(Question::getQuizId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        attempt.setSourceQuizIds(new ArrayList<>(sourceQuizIds));
        attempt.setRequestSource(source);
        attempt.setRequestParams(params);

        return attempt;
    }

    public Set<String> findQuizIdsFromAttemptSources(List<String> knowledgeIds, List<String> topicIds, List<String> quizIds) {
        Set<String> allTopicIds = new HashSet<>();
        Set<String> allQuizIds = new HashSet<>();

        // 1. Obtener topics por knowledges
        if (knowledgeIds != null && !knowledgeIds.isEmpty()) {
            List<Topic> topicsFromKnowledge = topicService.findByKnowledgeIds(knowledgeIds);
            topicsFromKnowledge.forEach(topic -> allTopicIds.add(topic.getId()));
        }

        // 2. Agregar topics directos
        if (topicIds != null && !topicIds.isEmpty()) {
            allTopicIds.addAll(topicIds);
        }

        // 3. Obtener quizzes desde topics
        if (!allTopicIds.isEmpty()) {
            List<Quiz> quizzesFromTopics = quizService.findByTopicIds(new ArrayList<>(allTopicIds));
            quizzesFromTopics.forEach(quiz -> allQuizIds.add(quiz.getId()));
        }

        // 4. Agregar quizzes directos
        if (quizIds != null && !quizIds.isEmpty()) {
            List<Quiz> directQuizzes = quizService.findByIds(quizIds);
            directQuizzes.forEach(quiz -> allQuizIds.add(quiz.getId()));
        }

        if (allQuizIds.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron cuestionarios con las fuentes proporcionadas.");
        }

        return allQuizIds;
    }

}
