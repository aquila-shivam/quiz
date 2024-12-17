package com.example.quiz.model;

import com.example.quiz.model.QuizSession;
import com.example.quiz.model.Question;

public class QuizStats {
    private int totalQuestions;
    private int correctAnswers;
    private int incorrectAnswers;

    public QuizStats(QuizSession session) {
        this.totalQuestions = session.getAnswers().size();
        this.correctAnswers = (int) session.getAnswers().stream().filter(answer -> answer.isCorrect()).count();
        this.incorrectAnswers = totalQuestions - correctAnswers;
    }

    // Getters and Setters
    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(int incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }
}

