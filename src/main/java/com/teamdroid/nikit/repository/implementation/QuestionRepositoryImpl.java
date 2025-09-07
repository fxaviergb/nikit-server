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

import java.util.List;

@Repository
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public QuestionRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Question> findRandomByQuizId(String quizId, Integer questionCount) {
        int safeCount = (questionCount == null || questionCount < 1) ? 10 : questionCount;

        MatchOperation matchStage = Aggregation.match(Criteria.where("quizId").is(quizId));
        SampleOperation sampleStage = Aggregation.sample(safeCount);

        Aggregation aggregation = Aggregation.newAggregation(matchStage, sampleStage);

        return mongoTemplate.aggregate(aggregation, "question", Question.class).getMappedResults();
    }
}

