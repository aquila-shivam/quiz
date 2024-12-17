package com.example.quiz.service;

import com.example.quiz.model.Question;
import com.example.quiz.repository.QuestionRepository;
import com.example.quiz.repository.QuizSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuizService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizSessionRepository quizSessionRepository;

    public Question getRandomQuestion() {
        List<Question> questions = questionRepository.findAll();
        Random random = new Random();
        return questions.get(random.nextInt(questions.size()));
    }
}
