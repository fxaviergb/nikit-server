package com.teamdroid.nikit.service.model;

import com.teamdroid.nikit.dto.request.OptionRequest;
import com.teamdroid.nikit.entity.Answer;
import com.teamdroid.nikit.entity.Audit;
import com.teamdroid.nikit.entity.Option;
import com.teamdroid.nikit.mapper.OptionMapper;
import com.teamdroid.nikit.repository.model.OptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private AnswerService answerService;

    private final OptionMapper optionMapper;

    public void createOption(OptionRequest request, String questionId, String userId, Audit audit) {
        Option option = optionMapper.toEntity(request);
        option.setQuestionId(questionId);
        option.setUserId(userId);
        option.setAudit(audit);
        option.setAnswer(optionMapper.toAnswer(request.getAnswer()));
        optionRepository.save(option);
    }
}
