package com.example.quiz.repository;

import com.example.quiz.model.QuizSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizSessionRepository extends JpaRepository<QuizSession, Long> {
}

