package com.teamdroid.nikit.mapper;

import com.teamdroid.nikit.dto.TopicQuizDTO;
import com.teamdroid.nikit.dto.TopicDTO;
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

    default List<TopicQuizDTO> toSimpleQuizDTOList(Topic topic) {
        if (topic.getQuizzes() == null) return List.of();

        return topic.getQuizzes().stream().map(this::toSimpleQuizDTO).collect(Collectors.toList());
    }

    default TopicQuizDTO toSimpleQuizDTO(Quiz quiz) {
        TopicQuizDTO dto = new TopicQuizDTO();
        dto.setId(quiz.getId());
        dto.setName(quiz.getName());
        dto.setDescription(quiz.getDescription());
        return dto;
    }

}
