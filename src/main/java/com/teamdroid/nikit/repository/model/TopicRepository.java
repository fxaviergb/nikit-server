package com.teamdroid.nikit.repository.model;

import com.teamdroid.nikit.entity.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends MongoRepository<Topic, String> {
    List<Topic> findByKnowledgeId(String knowledgeId);
}
