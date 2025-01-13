package com.teamdroid.nikit.mapper;

import com.teamdroid.nikit.dto.QuizzyExecutionDTO;
import com.teamdroid.nikit.entity.execution.QuizzyExecution;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuizzyExecutionMapper {

    QuizzyExecutionMapper INSTANCE = Mappers.getMapper(QuizzyExecutionMapper.class);

    QuizzyExecutionDTO toDTO(QuizzyExecution quizzyExecution);

    QuizzyExecution toEntity(QuizzyExecutionDTO quizzyExecutionDTO);
}
