package com.teamdroid.nikit.service.model;

import com.teamdroid.nikit.entity.Answer;
import com.teamdroid.nikit.entity.Option;
import com.teamdroid.nikit.repository.model.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private AnswerService answerService;

    /**
     * Creates a single option.
     *
     * @param option The option to be created.
     * @return The saved option.
     */
    public Option create(Option option) {
        Assert.notNull(option, "The option cannot be null");
        Assert.notNull(option.getAnswer(), "The answer associated with the option cannot be null");

        Answer answer = answerService.create(option.getAnswer());
        option.initializeAnswer(answer);
        return optionRepository.save(option);
    }

    /**
     * Creates a list of options atomically.
     *
     * @param options List of options to be created.
     * @return List of saved options.
     */
    public List<Option> create(List<Option> options) {
        Assert.notNull(options, "The list of options cannot be null");

        return options.stream()
                .map(this::create)
                .collect(Collectors.toList());
    }
}
