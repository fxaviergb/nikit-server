package com.teamdroid.nikit.service.model;

import com.teamdroid.nikit.entity.*;
import com.teamdroid.nikit.repository.model.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public Answer create(Answer answer) {
        return answerRepository.save(answer);
    }
}
