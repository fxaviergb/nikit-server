package com.teamdroid.nikit.controller.model;

import com.teamdroid.nikit.dto.*;
import com.teamdroid.nikit.dto.request.KnowledgeRequest;
import com.teamdroid.nikit.entity.Knowledge;
import com.teamdroid.nikit.entity.Topic;
import com.teamdroid.nikit.mapper.KnowledgeMapper;
import com.teamdroid.nikit.mapper.TopicMapper;
import com.teamdroid.nikit.service.model.KnowledgeService;
import com.teamdroid.nikit.service.model.TopicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private TopicService topicService;

    private final KnowledgeMapper knowledgeMapper;

    private final TopicMapper topicMapper;

    @PostMapping("/full")
    public ResponseEntity<KnowledgeDTO> createFull(@RequestBody KnowledgeRequest request,
                                             @RequestHeader(value = "X-User-Id", required = false) String userId) {
        String effectiveUserId = Optional.ofNullable(userId).orElse("system-user");
        Knowledge knowledge = knowledgeService.createFullStructure(request, effectiveUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(knowledgeMapper.toDTO(knowledge));
    }

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

        List<Topic> topics = topicService.findByKnowledgeId(knowledgeId);

        if (topics.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<TopicDTO> topicDTOs = topics.stream()
                .map(topicMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(topicDTOs);
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

    @PostMapping("/{knowledgeId}/topics")
    public ResponseEntity<List<TopicDTO>> addTopicsToKnowledge(
            @PathVariable String knowledgeId,
            @RequestBody List<TopicCreateDTO> topicCreateDTOs) {

        Optional<Knowledge> knowledgeOpt = knowledgeService.getById(knowledgeId);
        if (knowledgeOpt.isEmpty()) {
            return ResponseEntity.notFound().build(); // TODO Mostrar Knowledge not found
        }

        List<TopicDTO> createdTopics = topicService.createTopicsForKnowledge(knowledgeId, topicCreateDTOs);
        return ResponseEntity.ok(createdTopics);
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
