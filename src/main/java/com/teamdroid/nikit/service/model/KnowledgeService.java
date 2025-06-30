package com.teamdroid.nikit.service.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamdroid.nikit.dto.KnowledgeUpdatePartialDTO;
import com.teamdroid.nikit.dto.request.KnowledgeRequest;
import com.teamdroid.nikit.dto.request.TopicRequest;
import com.teamdroid.nikit.entity.*;
import com.teamdroid.nikit.logging.TDLogger;
import com.teamdroid.nikit.mapper.KnowledgeMapper;
import com.teamdroid.nikit.repository.model.KnowledgeRepository;
import com.teamdroid.nikit.shared.audit.AuditFactory;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class KnowledgeService {

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    @Autowired
    private TopicService topicService;

    private final KnowledgeMapper knowledgeMapper;

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeService.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Knowledge createFullStructure(KnowledgeRequest request, String userId) {
        Audit audit = AuditFactory.create(userId);
        Knowledge knowledge = knowledgeMapper.toEntity(request);
        knowledge.setUserId(userId);
        knowledge.setAudit(audit);
        knowledge = knowledgeRepository.save(knowledge);

        for (TopicRequest topicReq : request.getTopics()) {
            topicService.createTopicWithChildren(topicReq, knowledge.getId(), userId, audit);
        }

        return knowledge;
    }

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

    public Knowledge updatePartial(String id, KnowledgeUpdatePartialDTO dto) {
        Knowledge knowledge = knowledgeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Knowledge not found"));
        if (dto.getName() != null && !dto.getName().isBlank()) {
            knowledge.setName(dto.getName());
        }
        return knowledgeRepository.save(knowledge);
    }

}
