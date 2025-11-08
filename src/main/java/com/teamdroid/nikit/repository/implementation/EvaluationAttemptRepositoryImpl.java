package com.teamdroid.nikit.repository.implementation;

import com.teamdroid.nikit.entity.evaluation.EvaluationAttempt;
import com.teamdroid.nikit.repository.model.EvaluationAttemptRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class EvaluationAttemptRepositoryImpl implements EvaluationAttemptRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public EvaluationAttemptRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Búsqueda de intentos de evaluación con filtros dinámicos y ordenamiento personalizado.
     */
    @Override
    public List<EvaluationAttempt> searchAttemptsBySource(Set<String> quizIds, String queryType, String userId) {
        List<Criteria> criteriaList = new ArrayList<>();

        if (quizIds != null && !quizIds.isEmpty()) {
            criteriaList.add(Criteria.where("quiz.sourceQuizIds").in(quizIds));
        }

        if (queryType != null && !queryType.isBlank()) {
            criteriaList.add(Criteria.where("quiz.type").is(queryType));
        }

        if (userId != null && !userId.isBlank()) {
            criteriaList.add(Criteria.where("audit.createdBy").is(userId));
        }

        // Filtros para excluir attempts con grade incompleto
        criteriaList.add(Criteria.where("grade.score").ne(null));
        criteriaList.add(Criteria.where("grade.maximumScore").ne(null));
        criteriaList.add(Criteria.where("grade.createdDate").ne(null));

        Criteria finalCriteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
        Query query = new Query(finalCriteria);

        // Orden Desc por defecto
        query.with(Sort.by(Sort.Direction.DESC, "executionDate"));

        return mongoTemplate.find(query, EvaluationAttempt.class, "evaluation_attempt");
    }

}
