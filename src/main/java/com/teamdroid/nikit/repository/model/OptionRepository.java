package com.teamdroid.nikit.repository.model;

import com.teamdroid.nikit.entity.Option;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends MongoRepository<Option, String> {
}
