package com.teamdroid.nikit.repository.model;

import com.teamdroid.nikit.entity.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends MongoRepository<Topic, String> {
}
