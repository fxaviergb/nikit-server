package com.teamdroid.nikit.repository;

import com.teamdroid.nikit.entity.Questionnaire;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionnaireRepository extends MongoRepository<Questionnaire, String> {
}
