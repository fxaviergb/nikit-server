package com.teamdroid.wise.mapper;

import com.teamdroid.wise.dto.KnowledgeCreateDTO;
import com.teamdroid.wise.dto.KnowledgeDTO;
import com.teamdroid.wise.entity.Knowledge;
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
