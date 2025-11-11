package com.sophia.quiz_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sophia.quiz_service.model.QuestionWrapper;
import com.sophia.quiz_service.model.QuizDataTranferObject;
import com.sophia.quiz_service.model.Response;
import com.sophia.quiz_service.service.QuizService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.core.env.Environment;


@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    Environment env;

    private QuizService service;
    @Autowired
    public void setQuizServcie(QuizService service){
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<List<Integer>> createQuiz(@RequestBody QuizDataTranferObject quizDTO) {
        System.out.println("Quiz service running on port: " + env.getProperty("local.server.port"));
        return service.createQuiz(quizDTO.getCategory(), quizDTO.getNumQuestions(), quizDTO.getTitle());
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizById(@PathVariable int id) {
        return service.getQuizById(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuizAndGetScore(@PathVariable int id, @RequestBody List<Response> responses){
        return service.submitQuizAndGetScore(id, responses);
    }
}
