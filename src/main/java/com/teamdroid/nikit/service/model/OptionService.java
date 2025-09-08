package com.teamdroid.nikit.service.model;

import com.teamdroid.nikit.dto.request.OptionRequest;
import com.teamdroid.nikit.entity.Audit;
import com.teamdroid.nikit.entity.Option;
import com.teamdroid.nikit.mapper.OptionMapper;
import com.teamdroid.nikit.repository.model.OptionRepository;
import com.teamdroid.nikit.shared.audit.AuditFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;

    private final OptionMapper optionMapper;

    public void createOption(OptionRequest request, String questionId, String userId) {
        Option option = optionMapper.toEntity(request);
        option.setQuestionId(questionId);
        option.setAudit(AuditFactory.create(userId));
        option.setAnswer(optionMapper.toAnswer(request.getAnswer()));
        optionRepository.save(option);
    }

    public List<Option> findByQuestionId(String questionId) {
        return optionRepository.findByQuestionId(questionId);
    }

}
