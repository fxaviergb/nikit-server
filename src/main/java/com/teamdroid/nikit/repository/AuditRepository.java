package com.teamdroid.nikit.repository;

import com.teamdroid.nikit.entity.Audit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends MongoRepository<Audit, String> {
}
