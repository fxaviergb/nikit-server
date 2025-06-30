package com.teamdroid.nikit.dto;

import lombok.Data;

import java.util.List;

@Data
public class TopicWithQuizzesDTO {
    private String id;
    private String name;
    private String description;
    private String knowledgeId;
    private List<QuizDTO> quizzes;
}
