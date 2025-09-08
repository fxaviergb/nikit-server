package com.teamdroid.nikit.mapper;

import com.teamdroid.nikit.dto.TopicDTO;
import com.teamdroid.nikit.dto.TopicQuizDTO;
import com.teamdroid.nikit.dto.request.QuestionRequest;
import com.teamdroid.nikit.dto.request.TopicRequest;
import com.teamdroid.nikit.entity.Question;
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
public interface QuestionMapper {

    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    Question toEntity(QuestionRequest request);

}
