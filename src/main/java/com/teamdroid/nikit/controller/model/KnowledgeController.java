package com.teamdroid.nikit.controller.model;

import com.teamdroid.nikit.dto.KnowledgeCreateDTO;
import com.teamdroid.nikit.dto.KnowledgeDTO;
import com.teamdroid.nikit.dto.KnowledgeUpdatePartialDTO;
import com.teamdroid.nikit.dto.TopicDTO;
import com.teamdroid.nikit.entity.Knowledge;
import com.teamdroid.nikit.entity.Topic;
import com.teamdroid.nikit.mapper.KnowledgeMapper;
import com.teamdroid.nikit.mapper.TopicMapper;
import com.teamdroid.nikit.service.model.KnowledgeService;
import com.teamdroid.nikit.service.model.TopicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/knowledge")
@AllArgsConstructor
public class KnowledgeController {

    @Autowired
    private KnowledgeService knowledgeService;

    private final KnowledgeMapper knowledgeMapper;

    private final TopicMapper topicMapper;

    @GetMapping
    public ResponseEntity<List<KnowledgeDTO>> getAllKnowledge() {
        List<KnowledgeDTO> knowledgeDTOs = knowledgeService.getAll()
                .stream()
                .map(knowledgeMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(knowledgeDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KnowledgeDTO> getKnowledgeById(@PathVariable String id) {
        return knowledgeService.getById(id)
                .map(knowledgeMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{knowledgeId}/topics")
    public ResponseEntity<List<TopicDTO>> getTopicsByKnowledgeId(@PathVariable String knowledgeId) {
        Optional<Knowledge> knowledgeOpt = knowledgeService.getById(knowledgeId);

        if (knowledgeOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Knowledge knowledge = knowledgeOpt.get();

        List<TopicDTO> topics = knowledge.getTopics()
                .stream()
                .map(topicMapper::toDTO)
                .collect(Collectors.toList());

        if (topics.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(topics);
    }

    @PostMapping
    public ResponseEntity<KnowledgeDTO> createKnowledge(@Valid @RequestBody KnowledgeCreateDTO knowledgeCreateDTO) {
        var knowledge = knowledgeMapper.toEntityCreation(knowledgeCreateDTO);
        var createdKnowledge = knowledgeService.create(knowledge);
        return ResponseEntity.ok(knowledgeMapper.toDTO(createdKnowledge));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KnowledgeDTO> updateKnowledge(@PathVariable String id, @RequestBody KnowledgeDTO knowledgeDTO) {
        var knowledge = knowledgeMapper.toEntity(knowledgeDTO);
        var updatedKnowledge = knowledgeService.update(id, knowledge);
        return ResponseEntity.ok(knowledgeMapper.toDTO(updatedKnowledge));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKnowledge(@PathVariable String id) {
        knowledgeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/full")
    public ResponseEntity<Knowledge> createFullKnowledge(@RequestBody Knowledge knowledge) {
        var createdKnowledge = knowledgeService.createFull(knowledge);
        return ResponseEntity.ok(createdKnowledge);
    }

    @PostMapping("/{knowledgeId}/topics")
    public ResponseEntity<Knowledge> addTopicsToKnowledge(@PathVariable String knowledgeId, @RequestBody List<Topic> topics) {
        var knowledge = knowledgeService.addTransientTopics(knowledgeId, topics);
        return ResponseEntity.ok(knowledge);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<KnowledgeDTO> updatePartialKnowledge(
            @PathVariable String id,
            @RequestBody KnowledgeUpdatePartialDTO dto
    ) {
        var updated = knowledgeService.updatePartial(id, dto);
        return ResponseEntity.ok(knowledgeMapper.toDTO(updated));
    }
}
