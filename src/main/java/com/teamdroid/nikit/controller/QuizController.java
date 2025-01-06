package com.teamdroid.nikit.controller;

import com.teamdroid.nikit.entity.Question;
import com.teamdroid.nikit.entity.Quiz;
import com.teamdroid.nikit.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/quiz")
@AllArgsConstructor
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/{topicId}")
    public Quiz createQuizForTopic(@PathVariable String topicId, @RequestBody Quiz quiz) {
        return quizService.createQuizForTopic(topicId, quiz);
    }

    @PostMapping("/{quizId}/questions")
    public Quiz addQuestionsToQuiz(@PathVariable String quizId, @RequestBody List<Question> questions) {
        return quizService.addQuestionsToQuiz(quizId, questions);
    }
}
