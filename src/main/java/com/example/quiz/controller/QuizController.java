package com.example.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.quiz.repository.*;
import com.example.quiz.model.*;


import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizSessionRepository quizSessionRepository;

    // Endpoint to add a new question
    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        questionRepository.save(question);
        return ResponseEntity.ok("Question added successfully!");
    }

    // 1. Start a new quiz session
    @PostMapping("/start")
    public ResponseEntity<QuizSession> startNewQuiz() {
        QuizSession session = new QuizSession();
        quizSessionRepository.save(session);
        return ResponseEntity.ok(session);
    }

    // 2. Get a random multiple-choice question
    @GetMapping("/{sessionId}/question")
    public ResponseEntity<Question> getRandomQuestion(@PathVariable Long sessionId) {
        Optional<QuizSession> session = quizSessionRepository.findById(sessionId);
        if (session.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<Question> questions = questionRepository.findAll();
        if (questions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Random random = new Random();
        Question question = questions.get(random.nextInt(questions.size()));
        return ResponseEntity.ok(question);
    }

    // 3. Submit an answer
    @PostMapping("/{sessionId}/answer")
    public ResponseEntity<String> submitAnswer(@PathVariable Long sessionId, @RequestParam Long questionId, @RequestParam String answer) {
        Optional<QuizSession> session = quizSessionRepository.findById(sessionId);
        Optional<Question> question = questionRepository.findById(questionId);

        if (session.isEmpty() || question.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid session or question ID");
        }

        QuizSession quizSession = session.get();
        Question q = question.get();

        boolean isCorrect = q.getCorrectAnswer().equalsIgnoreCase(answer);
        quizSession.addAnswer(q, isCorrect);
        quizSessionRepository.save(quizSession);

        return ResponseEntity.ok(isCorrect ? "Correct answer!" : "Wrong answer.");
    }

    // 4. Get total questions answered and correctness details
    @GetMapping("/{sessionId}/stats")
    public ResponseEntity<QuizStats> getQuizStats(@PathVariable Long sessionId) {
        Optional<QuizSession> session = quizSessionRepository.findById(sessionId);
        if (session.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        QuizStats stats = new QuizStats(session.get());
        return ResponseEntity.ok(stats);
    }
}
