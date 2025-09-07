package com.teamdroid.nikit.controller;

import com.teamdroid.nikit.dto.TopicDTO;
import com.teamdroid.nikit.dto.TopicQuizDTO;
import com.teamdroid.nikit.dto.TopicUpdatePartialDTO;
import com.teamdroid.nikit.dto.TopicWithQuizzesDTO;
import com.teamdroid.nikit.dto.request.QuizRequest;
import com.teamdroid.nikit.dto.request.TopicRequest;
import com.teamdroid.nikit.entity.Quiz;
import com.teamdroid.nikit.entity.Topic;
import com.teamdroid.nikit.mapper.QuizMapper;
import com.teamdroid.nikit.mapper.TopicMapper;
import com.teamdroid.nikit.service.model.QuizService;
import com.teamdroid.nikit.service.model.TopicService;
import com.teamdroid.nikit.shared.audit.AuditFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/topic")
@AllArgsConstructor
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private QuizService quizService;

    private final TopicMapper topicMapper;

    private final QuizMapper quizMapper;

    @GetMapping
    public ResponseEntity<List<TopicDTO>> getAllTopics() {
        List<TopicDTO> topicDTOs = topicService.getAll()
                .stream()
                .map(topicMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(topicDTOs);
    }

    @GetMapping("/{topicId}/quizzes")
    public ResponseEntity<List<TopicQuizDTO>> getTopicQuizzesByTopicId(@PathVariable String topicId) {
        Topic topic = topicService.findById(topicId);
        // Obtenemos todos los quizzes relacionados con este tópico
        List<Quiz> quizzes = quizService.findByTopicId(topic.getId());
        if (quizzes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<TopicQuizDTO> quizDTOs = quizzes.stream()
                .map(quizMapper::toTopicQuizDTO)
                .toList();
        return ResponseEntity.ok(quizDTOs);
    }

    @PostMapping("/{knowledgeId}")
    public ResponseEntity<TopicDTO> createTopicForKnowledge(
            @PathVariable String knowledgeId,
            @RequestBody TopicRequest topicRequest
    ) {
        String userId = "system"; // TODO obtener de JWT
        Topic topic = topicService.createTopicWithChildren(topicRequest, knowledgeId, userId);
        return ResponseEntity.ok(topicMapper.toDTO(topic));
    }

    @PostMapping("/{topicId}/quizzes")
    public ResponseEntity<TopicWithQuizzesDTO> addQuizzesToTopic(
            @PathVariable String topicId,
            @RequestBody List<QuizRequest> quizzes
    ) {
        Topic topic = topicService.findById(topicId);
        String userId =  "system"; // TODO obtener de JWT
        for (QuizRequest quizRequest : quizzes) {
            quizService.createQuizWithChildren(quizRequest, topicId, userId);
        }
        List<Quiz> quizzesCreated = quizService.findByTopicId(topicId);
        TopicWithQuizzesDTO response = topicMapper.toTopicWithQuizzesDTO(topic, quizzesCreated);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{topicId}")
    public ResponseEntity<TopicDTO> updatePartialTopic(@PathVariable String topicId,
            @RequestBody TopicUpdatePartialDTO dto
    ) {
        var updated = topicService.updatePartial(topicId, dto);
        return ResponseEntity.ok(topicMapper.toDTO(updated));
    }

}
