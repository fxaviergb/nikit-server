package com.teamdroid.wise.service;

import com.teamdroid.wise.entity.Knowledge;
import com.teamdroid.wise.repository.KnowledgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KnowledgeService {

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    public List<Knowledge> getAllKnowledge() {
        return knowledgeRepository.findAll();
    }

    public Optional<Knowledge> getKnowledgeById(String id) {
        return knowledgeRepository.findById(id);
    }

    public Knowledge createKnowledge(Knowledge knowledge) {
        return knowledgeRepository.save(knowledge);
    }

    public Knowledge updateKnowledge(String id, Knowledge knowledge) {
        knowledge.setId(id);
        return knowledgeRepository.save(knowledge);
    }

    public void deleteKnowledge(String id) {
        knowledgeRepository.deleteById(id);
    }

}
