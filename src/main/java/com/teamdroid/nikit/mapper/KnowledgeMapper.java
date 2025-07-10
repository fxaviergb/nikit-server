package com.teamdroid.nikit.mapper;

import com.teamdroid.nikit.dto.KnowledgeCreateDTO;
import com.teamdroid.nikit.dto.KnowledgeDTO;
import com.teamdroid.nikit.entity.Knowledge;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface KnowledgeMapper {

    KnowledgeMapper INSTANCE = Mappers.getMapper(KnowledgeMapper.class);

    KnowledgeDTO toDTO(Knowledge knowledge);

    Knowledge toEntityCreation(KnowledgeCreateDTO knowledgeCreateDTO);

    Knowledge toEntity(KnowledgeDTO knowledgeDTO);
}
