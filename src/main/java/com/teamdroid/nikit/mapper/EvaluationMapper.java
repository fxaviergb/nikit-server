package com.teamdroid.nikit.mapper;

import com.teamdroid.nikit.dto.EvaluationDTO;
import com.teamdroid.nikit.entity.evaluation.Evaluation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EvaluationMapper {

    EvaluationMapper INSTANCE = Mappers.getMapper(EvaluationMapper.class);

    EvaluationDTO toDTO(Evaluation quizzyExecution);

    Evaluation toEntity(EvaluationDTO quizzyExecutionDTO);
}
