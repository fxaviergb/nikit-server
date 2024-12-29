package com.teamdroid.wise.repository;

import com.teamdroid.wise.entity.Knowledge;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KnowledgeRepository extends MongoRepository<Knowledge, String> {
}
