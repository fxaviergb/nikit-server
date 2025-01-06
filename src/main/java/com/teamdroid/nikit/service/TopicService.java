package com.teamdroid.nikit.service;

import com.teamdroid.nikit.entity.*;
import com.teamdroid.nikit.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private QuizService quizService;

    @Autowired
    private KnowledgeService knowledgeService;

    public Topic findById(String topicId) {
        return topicRepository.findById(topicId).orElseThrow(
                () -> new RuntimeException("Topic not found"));
    }

    public Topic create(Topic topic) {
        Assert.notNull(topic, "The topic cannot be null");

        List<Quiz> quizzes = quizService.create(topic.getQuizzes());
        topic.initializeQuizzes(quizzes);
        return topicRepository.save(topic);
    }

    public List<Topic> create(List<Topic> topics) {
        Assert.notNull(topics, "The list of topics cannot be null");

        return topics.stream()
                .map(this::create)
                .collect(Collectors.toList());
    }

    public Topic createFullForKnowledge(String knowledgeId, Topic topic) {
        Assert.notNull(knowledgeId, "The knowledge Id cannot be null");
        Assert.notNull(topic, "The topic cannot be null");

        Knowledge knowledge = knowledgeService.findById(knowledgeId);
        Topic createdTopic = create(topic);
        knowledgeService.addPersistentTopics(knowledge, createdTopic);
        return createdTopic;
    }

    public Topic addTransientQuizzes(String topicId, List<Quiz> quizzes) {
        Topic topic = findById(topicId);
        List<Quiz> createdQuizzes = quizService.create(quizzes);
        topic.addQuizzes(createdQuizzes);
        return topicRepository.save(topic);
    }

    public Topic addPersistentQuizzes(Topic topic, Quiz... quizzes) {
        topic.addQuizzes(quizzes);
        return topicRepository.save(topic);
    }
}
