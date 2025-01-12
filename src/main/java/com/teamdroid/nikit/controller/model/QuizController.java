package com.teamdroid.nikit.controller.model;

import com.teamdroid.nikit.entity.Question;
import com.teamdroid.nikit.entity.Quiz;
import com.teamdroid.nikit.service.model.QuizService;
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
        return quizService.createFullForTopic(topicId, quiz);
    }

    @PostMapping("/{quizId}/questions")
    public Quiz addQuestionsToQuiz(@PathVariable String quizId, @RequestBody List<Question> questions) {
        return quizService.addTransientQuestions(quizId, questions);
    }
}
