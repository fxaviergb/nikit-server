package com.teamdroid.nikit.repository.implementation;

import com.teamdroid.nikit.entity.Question;
import com.teamdroid.nikit.repository.model.QuestionRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Repository
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public QuestionRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Question> findRandomByQuizId(String quizId, Integer questionCount) {
        if (quizId == null || quizId.isBlank()) return Collections.emptyList();
        return findRandomQuestions(Criteria.where("quizId").is(quizId), questionCount);
    }

    @Override
    public List<Question> findRandomByQuizIds(Set<String> quizIds, Integer questionCount) {
        if (quizIds == null || quizIds.isEmpty()) return Collections.emptyList();
        return findRandomQuestions(Criteria.where("quizId").in(quizIds), questionCount);
    }

    /**
     * Método para ejecutar un $match + $sample sobre la colección "question".
     */
    private List<Question> findRandomQuestions(Criteria matchCriteria, Integer count) {
        int safeCount = (count == null || count < 1) ? 10 : count;

        MatchOperation matchStage = Aggregation.match(matchCriteria);
        SampleOperation sampleStage = Aggregation.sample(safeCount);

        Aggregation aggregation = Aggregation.newAggregation(matchStage, sampleStage);

        return mongoTemplate.aggregate(aggregation, "question", Question.class).getMappedResults();
    }
}
