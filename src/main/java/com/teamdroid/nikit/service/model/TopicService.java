package com.teamdroid.nikit.service.model;

import com.teamdroid.nikit.dto.TopicCreateDTO;
import com.teamdroid.nikit.dto.TopicDTO;
import com.teamdroid.nikit.dto.TopicUpdatePartialDTO;
import com.teamdroid.nikit.dto.request.QuizRequest;
import com.teamdroid.nikit.dto.request.TopicRequest;
import com.teamdroid.nikit.entity.*;
import com.teamdroid.nikit.mapper.TopicMapper;
import com.teamdroid.nikit.repository.model.TopicRepository;
import com.teamdroid.nikit.shared.audit.AuditFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private QuizService quizService;

    private final TopicMapper topicMapper;


    public Topic createTopicWithChildren(TopicRequest request, String knowledgeId, String userId, Audit audit) {
        Topic topic = topicMapper.toEntity(request);
        topic.setKnowledgeId(knowledgeId);
        topic.setUserId(userId);
        topic.setAudit(audit);
        topic = topicRepository.save(topic);

        if (request.getQuizzes() != null && !request.getQuizzes().isEmpty()) {
            for (QuizRequest quizReq : request.getQuizzes()) {
                quizService.createQuizWithChildren(quizReq, topic.getId(), userId, audit);
            }
        }

        return topic;
    }

    public List<Topic> findByKnowledgeId(String knowledgeId) {
        return topicRepository.findByKnowledgeId(knowledgeId);
    }

    public List<TopicDTO> createTopicsForKnowledge(String knowledgeId, List<TopicCreateDTO> topicCreateDTOs) {
        List<TopicDTO> created = new ArrayList<>();
        for (TopicCreateDTO dto : topicCreateDTOs) {
            Topic topic = topicMapper.toEntity(dto);
            topic.setKnowledgeId(knowledgeId);
            //topic.setAudit(AuditFactory.create()); // TODO
            Topic saved = topicRepository.save(topic);
            created.add(topicMapper.toDTO(saved));
        }
        return created;
    }

    public List<Topic> getAll() {
        return topicRepository.findAll();
    }

    public Topic findById(String topicId) {
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
