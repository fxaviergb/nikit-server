package com.teamdroid.nikit.controller;

import com.teamdroid.nikit.dto.KnowledgeCreateDTO;
import com.teamdroid.nikit.dto.KnowledgeDTO;
import com.teamdroid.nikit.entity.Knowledge;
import com.teamdroid.nikit.entity.Topic;
import com.teamdroid.nikit.mapper.KnowledgeMapper;
import com.teamdroid.nikit.service.KnowledgeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/api/knowledge")
@AllArgsConstructor
public class KnowledgeController {

    @Autowired
    private KnowledgeService knowledgeService;

    private final KnowledgeMapper knowledgeMapper;

    @GetMapping
    public ResponseEntity<List<KnowledgeDTO>> getAllKnowledge() {
        List<KnowledgeDTO> knowledgeDTOs = knowledgeService.getAllKnowledge()
                .stream()
                .map(knowledgeMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(knowledgeDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KnowledgeDTO> getKnowledgeById(@PathVariable String id) {
        return knowledgeService.getKnowledgeById(id)
                .map(knowledgeMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<KnowledgeDTO> createKnowledge(@RequestBody KnowledgeCreateDTO knowledgeCreateDTO) {
        var knowledge = knowledgeMapper.toEntityCreation(knowledgeCreateDTO);
        var createdKnowledge = knowledgeService.createKnowledge(knowledge);
        return ResponseEntity.ok(knowledgeMapper.toDTO(createdKnowledge));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KnowledgeDTO> updateKnowledge(@PathVariable String id, @RequestBody KnowledgeDTO knowledgeDTO) {
        var knowledge = knowledgeMapper.toEntity(knowledgeDTO);
        var updatedKnowledge = knowledgeService.updateKnowledge(id, knowledge);
        return ResponseEntity.ok(knowledgeMapper.toDTO(updatedKnowledge));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKnowledge(@PathVariable String id) {
        knowledgeService.deleteKnowledge(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/full")
    public ResponseEntity<Knowledge> createFullKnowledge(@RequestBody Knowledge knowledge) {
        var createdKnowledge = knowledgeService.createFullKnowledge(knowledge);
        return ResponseEntity.ok(createdKnowledge);
    }

    @PostMapping("/{knowledgeId}/topics")
    public ResponseEntity<Knowledge> addTopicsToKnowledge(@PathVariable String knowledgeId, @RequestBody List<Topic> topics) {
        var knowledge = knowledgeService.addTopicsToKnowledge(knowledgeId, topics);
        return ResponseEntity.ok(knowledge);
    }
}
