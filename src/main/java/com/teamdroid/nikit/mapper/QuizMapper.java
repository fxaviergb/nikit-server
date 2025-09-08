package com.teamdroid.nikit.mapper;

import com.teamdroid.nikit.dto.QuizDTO;
import com.teamdroid.nikit.dto.TopicQuizDTO;
import com.teamdroid.nikit.dto.request.AnswerRequest;
import com.teamdroid.nikit.dto.request.OptionRequest;
import com.teamdroid.nikit.dto.request.QuizRequest;
import com.teamdroid.nikit.entity.Answer;
import com.teamdroid.nikit.entity.Option;
import com.teamdroid.nikit.entity.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuizMapper {

    QuizMapper INSTANCE = Mappers.getMapper(QuizMapper.class);

    Quiz toEntity(QuizRequest request);

    default TopicQuizDTO toTopicQuizDTO(Quiz quiz) {
        TopicQuizDTO dto = new TopicQuizDTO();
        dto.setId(quiz.getId());
        dto.setName(quiz.getName());
        dto.setDescription(quiz.getDescription());
        return dto;
    }
}
