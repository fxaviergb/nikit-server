package com.teamdroid.nikit.controller;

import com.teamdroid.nikit.entity.Question;
import com.teamdroid.nikit.entity.Questionnaire;
import com.teamdroid.nikit.entity.Topic;
import com.teamdroid.nikit.service.QuestionnaireService;
import com.teamdroid.nikit.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/questionnaire")
@AllArgsConstructor
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @PostMapping("/{topicId}")
    public Questionnaire createQuestionnaireForTopic(@PathVariable String topicId, @RequestBody Questionnaire questionnaire) {
        return questionnaireService.createQuestionnaireForTopic(topicId, questionnaire);
    }

    @PostMapping("/{questionnaireId}/questions")
    public Questionnaire addQuestionsToQuestionnaire(@PathVariable String questionnaireId, @RequestBody List<Question> questions) {
        return questionnaireService.addQuestionsToQuestionnaire(questionnaireId, questions);
    }
}
