package com.teamdroid.nikit.repository.model;

import com.teamdroid.nikit.entity.Knowledge;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KnowledgeRepository extends MongoRepository<Knowledge, String> {
}
