package com.sophia.quiz_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sophia.quiz_service.feign.QuizInterface;
import com.sophia.quiz_service.model.QuestionWrapper;
import com.sophia.quiz_service.model.Quiz;
import com.sophia.quiz_service.model.Response;
import com.sophia.quiz_service.repository.QuizRepository;

@Service
public class QuizService {

    private QuizRepository quizRepository;
    @Autowired
    public void setQuizRepository(QuizRepository quizRepository){
        this.quizRepository = quizRepository;
    }

    private QuizInterface quizInterface;
    @Autowired
    public void setQuizInterface(QuizInterface quizInterface){
        this.quizInterface = quizInterface;
    }

    public ResponseEntity<List<Integer>> createQuiz(String category, int numQ, String title) {
        List<Integer> questionIds = quizInterface.getQuestionsForQuiz(category, numQ).getBody();//get questions from the response body
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questionIds);
        quizRepository.save(quiz);

        return new ResponseEntity<>(questionIds, HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizById(int id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        List<Integer> questionIds = quiz.get().getQuestionIds();
        List<QuestionWrapper> questions = quizInterface.getQuestionsFromIds(questionIds).getBody();

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<Integer> submitQuizAndGetScore(int id, List<Response> responses) {
        return quizInterface.getScore(responses);
    }
}
