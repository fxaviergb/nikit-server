package com.teamdroid.nikit.service.model;

import com.teamdroid.nikit.dto.TopicDTO;
import com.teamdroid.nikit.dto.TopicUpdatePartialDTO;
import com.teamdroid.nikit.dto.request.QuizRequest;
import com.teamdroid.nikit.dto.request.TopicRequest;
import com.teamdroid.nikit.entity.*;
import com.teamdroid.nikit.logging.TDLogger;
import com.teamdroid.nikit.mapper.TopicMapper;
import com.teamdroid.nikit.repository.model.TopicRepository;
import com.teamdroid.nikit.shared.audit.AuditFactory;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private QuizService quizService;

    private final TopicMapper topicMapper;

    private static final Logger logger = LoggerFactory.getLogger(TopicService.class);


    public Topic createTopicWithChildren(TopicRequest request, String knowledgeId, String userId) {
        Audit audit = AuditFactory.create(userId);
        Topic topic = topicMapper.toEntity(request);
        topic.setKnowledgeId(knowledgeId);
        topic.setAudit(audit);
        topic = topicRepository.save(topic);

        if (request.getQuizzes() != null && !request.getQuizzes().isEmpty()) {
            for (QuizRequest quizReq : request.getQuizzes()) {
                quizService.createQuizWithRelations(quizReq, topic.getId(), userId);
            }
        }

        return topic;
    }

    public List<Topic> findByKnowledgeId(String knowledgeId) {
        return topicRepository.findByKnowledgeId(knowledgeId);
    }

    public List<TopicDTO> createTopicsForKnowledge(String knowledgeId, List<TopicRequest> topicRequests, String userId) {
        List<TopicDTO> created = new ArrayList<>();
        for (TopicRequest r : topicRequests) {
            Topic topic = this.createTopicWithChildren(r, knowledgeId, userId);
            created.add(topicMapper.toDTO(topic));
        }
        return created;
    }

    public List<Topic> getAll() {
        return topicRepository.findAll();
    }

    public Topic findById(String topicId) {
        TDLogger.logF(logger, TDLogger.Level.INFO, "Getting topic with ID: {}", topicId);
        return topicRepository.findById(topicId).orElseThrow(
                () -> new RuntimeException("Topic not found"));
    }

    public Topic updatePartial(String topicId, TopicUpdatePartialDTO dto) {
        Topic topic = findById(topicId);

        if (dto.getName() != null && !dto.getName().isBlank()) {
            topic.setName(dto.getName());
        }

        if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
            topic.setDescription(dto.getDescription());
        }

        return topicRepository.save(topic);
    }

}
