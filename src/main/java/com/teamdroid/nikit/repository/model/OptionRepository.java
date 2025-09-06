package com.teamdroid.nikit.repository.model;

import com.teamdroid.nikit.entity.Option;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends MongoRepository<Option, String> {
    List<Option> findByQuestionId(String questionId);
}
