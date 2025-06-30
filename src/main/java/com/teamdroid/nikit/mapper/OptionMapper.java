package com.teamdroid.nikit.mapper;

import com.teamdroid.nikit.dto.request.AnswerRequest;
import com.teamdroid.nikit.dto.request.OptionRequest;
import com.teamdroid.nikit.dto.request.QuestionRequest;
import com.teamdroid.nikit.entity.Answer;
import com.teamdroid.nikit.entity.Option;
import com.teamdroid.nikit.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OptionMapper {

    OptionMapper INSTANCE = Mappers.getMapper(OptionMapper.class);

    Option toEntity(OptionRequest request);
    Answer toAnswer(AnswerRequest request);
}
