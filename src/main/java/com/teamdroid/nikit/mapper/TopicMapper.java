package com.teamdroid.nikit.mapper;

import com.teamdroid.nikit.dto.*;
import com.teamdroid.nikit.dto.request.TopicRequest;
import com.teamdroid.nikit.entity.Quiz;
import com.teamdroid.nikit.entity.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TopicMapper {

    TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

    TopicDTO toDTO(Topic topic);

    Topic toEntity(TopicDTO topicDTO);
    Topic toEntity(TopicCreateDTO topicCreateDTO);
    Topic toEntity(TopicRequest topicRequest);

    default TopicQuizDTO toSimpleQuizDTO(Quiz quiz) {
        TopicQuizDTO dto = new TopicQuizDTO();
        dto.setId(quiz.getId());
        dto.setName(quiz.getName());
        dto.setDescription(quiz.getDescription());
        return dto;
    }

    default TopicWithQuizzesDTO toTopicWithQuizzesDTO(Topic topic, List<Quiz> quizzes) {
        TopicWithQuizzesDTO dto = new TopicWithQuizzesDTO();
        dto.setId(topic.getId());
        dto.setName(topic.getName());
        dto.setDescription(topic.getDescription());
        dto.setKnowledgeId(topic.getKnowledgeId());
        dto.setQuizzes(quizzes.stream().map(this::toQuizDTO).toList());
        return dto;
    }

    default QuizDTO toQuizDTO(Quiz quiz) {
        QuizDTO dto = new QuizDTO();
        dto.setId(quiz.getId());
        dto.setName(quiz.getName());
        dto.setDescription(quiz.getDescription());
        return dto;
    }

}
