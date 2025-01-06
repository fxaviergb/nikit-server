package com.teamdroid.nikit.controller;

import com.teamdroid.nikit.entity.Quiz;
import com.teamdroid.nikit.entity.Topic;
import com.teamdroid.nikit.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/topic")
@AllArgsConstructor
public class TopicController {

    @Autowired
    private TopicService topicService;


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
