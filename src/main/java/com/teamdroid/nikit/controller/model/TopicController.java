package com.teamdroid.nikit.controller.model;

import com.teamdroid.nikit.dto.TopicDTO;
import com.teamdroid.nikit.dto.TopicQuizDTO;
import com.teamdroid.nikit.entity.Quiz;
import com.teamdroid.nikit.entity.Topic;
import com.teamdroid.nikit.mapper.TopicMapper;
import com.teamdroid.nikit.service.model.TopicService;
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

    private final TopicMapper topicMapper;

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
        List<TopicQuizDTO> quizzes = topicMapper.toSimpleQuizDTOList(topic);
        return ResponseEntity.ok(quizzes);
    }

    @PostMapping("/{knowledgeId}")
    public ResponseEntity<Topic> createTopicForKnowledge(@PathVariable String knowledgeId, @RequestBody Topic topic) {
        var createdTopic = topicService.createFullForKnowledge(knowledgeId, topic);
        return ResponseEntity.ok(createdTopic);
    }

    @PostMapping("/{topicId}/quizzes")
    public ResponseEntity<Topic> addQuizzesToTopic(@PathVariable String topicId, @RequestBody List<Quiz> quizzes) {
        var topic = topicService.addTransientQuizzes(topicId, quizzes);
        return ResponseEntity.ok(topic);
    }
}
