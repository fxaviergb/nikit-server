package com.teamdroid.nikit.service.model;

import com.teamdroid.nikit.dto.request.OptionRequest;
import com.teamdroid.nikit.entity.Option;
import com.teamdroid.nikit.mapper.OptionMapper;
import com.teamdroid.nikit.repository.model.OptionRepository;
import com.teamdroid.nikit.shared.audit.AuditFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    public void updateOption(Option existing, OptionRequest request, String userId) {
        existing.setOption(request.getOption());
        existing.setAnswer(optionMapper.toAnswer(request.getAnswer()));
        existing.setAudit(AuditFactory.update(existing.getAudit(), userId));
        optionRepository.save(existing);
    }

    public List<Option> findByQuestionId(String questionId) {
        return optionRepository.findByQuestionId(questionId);
    }

    public void deleteByQuestionId(String questionId) {
        List<Option> options = findByQuestionId(questionId);
        optionRepository.deleteAll(options);
    }

    public void syncOptions(String questionId, List<OptionRequest> incomingOptions, String userId) {
        List<Option> existingOptions = findByQuestionId(questionId);
        Map<String, Option> existingMap = existingOptions.stream()
                .collect(Collectors.toMap(Option::getId, o -> o));

        Set<String> retainedIds = new HashSet<>();

        for (OptionRequest optionReq : incomingOptions) {
            boolean isNew = optionReq.getId() == null || optionReq.getId().isBlank();

            if (isNew) {
                createOption(optionReq, questionId, userId);
            } else {
                Option existing = existingMap.get(optionReq.getId());
                if (existing != null) {
                    updateOption(existing, optionReq, userId);
                    retainedIds.add(existing.getId());
                }
            }
        }

        // Eliminar las opciones que ya no están
        List<Option> toDelete = existingOptions.stream()
                .filter(o -> !retainedIds.contains(o.getId()))
                .collect(Collectors.toList());

        optionRepository.deleteAll(toDelete);
    }
}
