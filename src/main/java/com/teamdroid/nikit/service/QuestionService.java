package com.teamdroid.nikit.service;

import com.teamdroid.nikit.entity.Option;
import com.teamdroid.nikit.entity.Question;
import com.teamdroid.nikit.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionService optionService;

    public Question create(Question question) {
        Assert.notNull(question, "The question cannot be null");

        List<Option> options = optionService.create(question.getOptions());
        question.initializeOptions(options);
        return questionRepository.save(question);
    }

    public List<Question> create(List<Question> questions) {
        Assert.notNull(questions, "The list of questions cannot be null");

        return questions.stream()
                .map(this::create)
                .collect(Collectors.toList());
    }
}
