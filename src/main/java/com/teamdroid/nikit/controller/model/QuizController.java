package com.teamdroid.nikit.controller.model;

import com.teamdroid.nikit.dto.QuizSummaryDTO;
import com.teamdroid.nikit.dto.TopicQuizDTO;
import com.teamdroid.nikit.entity.Question;
import com.teamdroid.nikit.entity.Quiz;
import com.teamdroid.nikit.entity.Topic;
import com.teamdroid.nikit.entity.evaluation.Evaluation;
import com.teamdroid.nikit.mapper.QuizSummaryMapper;
import com.teamdroid.nikit.model.view.QuizSummary;
import com.teamdroid.nikit.service.model.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz")
@AllArgsConstructor
public class QuizController {

    @Autowired
    private QuizService quizService;

    private final QuizSummaryMapper quizSummaryMapper;

    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable String quizId) {
        Quiz quiz = quizService.findById(quizId);
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/{topicId}")
    public Quiz createQuizForTopic(@PathVariable String topicId, @RequestBody Quiz quiz) {
        return quizService.createFullForTopic(topicId, quiz);
    }

    @PostMapping("/{quizId}/questions")
    public Quiz addQuestionsToQuiz(@PathVariable String quizId, @RequestBody List<Question> questions) {
        return quizService.addTransientQuestions(quizId, questions);
    }

    @GetMapping("/{quizId}/summary")
    public ResponseEntity<QuizSummaryDTO> getQuizSummaryByQuizId(@PathVariable String quizId) {
        QuizSummary quizSummary = quizService.findSummaryById(quizId);
        QuizSummaryDTO quizSummaryDTO = quizSummaryMapper.toQuizSummaryDTO(quizSummary);
        return ResponseEntity.ok(quizSummaryDTO);
    }
}
