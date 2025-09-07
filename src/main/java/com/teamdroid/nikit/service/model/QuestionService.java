package com.teamdroid.nikit.service.model;

import com.teamdroid.nikit.dto.request.OptionRequest;
import com.teamdroid.nikit.dto.request.QuestionRequest;
import com.teamdroid.nikit.entity.Audit;
import com.teamdroid.nikit.entity.Option;
import com.teamdroid.nikit.entity.Question;
import com.teamdroid.nikit.entity.Quiz;
import com.teamdroid.nikit.mapper.QuestionMapper;
import com.teamdroid.nikit.repository.model.QuestionRepository;
import com.teamdroid.nikit.shared.audit.AuditFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionService optionService;

    private final QuestionMapper questionMapper;

    public void createQuestionWithOptions(QuestionRequest request, String quizId, String userId) {
        // Convertimos el request en entidad
        Audit audit = AuditFactory.create(userId);
        Question question = questionMapper.toEntity(request);
        question.setQuizId(quizId);
        question.setQuestionVersion(1);
        question.setAudit(audit);

        // Guardamos la pregunta primero
        question = questionRepository.save(question);

        // Creamos cada opción asociándola a la pregunta creada
        for (OptionRequest optionReq : request.getOptions()) {
            optionService.createOption(optionReq, question.getId(), userId);
        }
    }

    public List<Question> findByQuizId(String quizId) {
        return questionRepository.findByQuizId(quizId);
    }

    public List<Question> findByQuizIdFull(String quizId) {
        List<Question> questions = findByQuizId(quizId);
        for(Question q : questions) {
            q.setOptions(optionService.findByQuestionId(q.getId()));
        }
        return questions;
    }

}
