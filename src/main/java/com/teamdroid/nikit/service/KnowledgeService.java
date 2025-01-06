package com.teamdroid.nikit.service;

import com.teamdroid.nikit.entity.*;
import com.teamdroid.nikit.repository.*;
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


    public List<Knowledge> getAll() {
        return knowledgeRepository.findAll();
    }

    public Optional<Knowledge> getById(String id) {
        return knowledgeRepository.findById(id);
    }

    public Knowledge create(Knowledge knowledge) {
        return knowledgeRepository.save(knowledge);
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

}
