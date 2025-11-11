package com.sophia.quiz_service.model;

import lombok.Data;

@Data
public class QuizDataTranferObject {
    private String category;
    private int numQuestions;
    private String title;
}
