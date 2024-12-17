package com.example.quiz.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Answer {
    private Long questionId;
    private boolean correct;

    // No-argument constructor
    public Answer() {
    }

    public Answer(Long questionId, boolean correct) {
        this.questionId = questionId;
        this.correct = correct;
    }

    // Getters and Setters
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
