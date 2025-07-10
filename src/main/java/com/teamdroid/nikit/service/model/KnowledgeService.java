package com.teamdroid.nikit.service.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamdroid.nikit.dto.KnowledgeUpdatePartialDTO;
import com.teamdroid.nikit.entity.*;
import com.teamdroid.nikit.logging.TDLogger;
import com.teamdroid.nikit.repository.model.KnowledgeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class KnowledgeService {

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    @Autowired
    private TopicService topicService;

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeService.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public List<Knowledge> getAll() {
        TDLogger.log(logger, TDLogger.Level.INFO, "Getting all knowledge groups");
        return knowledgeRepository.findAll();
    }

    public Optional<Knowledge> getById(String id) {
        TDLogger.logF(logger, TDLogger.Level.INFO, "Getting knowledge with ID: {}", id);
        return knowledgeRepository.findById(id);
    }

    public Knowledge create(Knowledge knowledge) {
        TDLogger.log(logger, TDLogger.Level.INFO, "Creating knowledge", knowledge);
        Knowledge saved = knowledgeRepository.save(knowledge);
        TDLogger.log(logger, TDLogger.Level.INFO, "Creating knowledge", knowledge);
        TDLogger.logF(logger, TDLogger.Level.INFO, "Knowledge created with ID: {}", saved.getId());
        return saved;
    }

    public Knowledge update(String id, Knowledge knowledge) {
        knowledge.setId(id);
        return knowledgeRepository.save(knowledge);
    }

    public void delete(String id) {
        knowledgeRepository.deleteById(id);
    }

    public Knowledge findById(String knowledgeId) {
        return knowledgeRepository.findById(knowledgeId).orElseThrow(
                () -> new RuntimeException("Knowledge not found"));
    }

    public Knowledge createFull(Knowledge knowledge) {
        Assert.notNull(knowledge, "The knowledge cannot be null");

        List<Topic> topics = topicService.create(knowledge.getTopics());
        knowledge.initializeTopics(topics);
        return knowledgeRepository.save(knowledge);
    }

    public Knowledge addTransientTopics(String knowledgeId, List<Topic> topics) {
        Knowledge knowledge = findById(knowledgeId);
        List<Topic> createdTopics = topicService.create(topics);
        knowledge.addTopics(createdTopics);
        return knowledgeRepository.save(knowledge);
    }

    public Knowledge addPersistentTopics(Knowledge knowledge, Topic... topics) {
        knowledge.addTopics(topics);
        return knowledgeRepository.save(knowledge);
    }

    public Knowledge updatePartial(String id, KnowledgeUpdatePartialDTO dto) {
        Knowledge knowledge = knowledgeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Knowledge not found"));
        if (dto.getName() != null && !dto.getName().isBlank()) {
            knowledge.setName(dto.getName());
        }
        return knowledgeRepository.save(knowledge);
    }

}
