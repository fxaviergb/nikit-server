package com.teamdroid.nikit.mapper;

import com.teamdroid.nikit.entity.Quiz;
import com.teamdroid.nikit.entity.evaluation.QuizAttempt;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuizAttemptFromQuizMapper {

    QuizAttemptFromQuizMapper INSTANCE = Mappers.getMapper(QuizAttemptFromQuizMapper.class);

    QuizAttempt from(Quiz quiz);
}
