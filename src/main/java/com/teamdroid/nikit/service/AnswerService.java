package com.teamdroid.nikit.service;

import com.teamdroid.nikit.entity.*;
import com.teamdroid.nikit.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public Answer create(Answer answer) {
        return answerRepository.save(answer);
    }
}
