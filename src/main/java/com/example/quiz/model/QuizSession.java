package com.example.quiz.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class QuizSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private final List<Answer> answers = new ArrayList<>();

    public void addAnswer(Question question, boolean isCorrect) {
        answers.add(new Answer(question.getId(), isCorrect));
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    // Other fields and methods
}
