// src/main/java/com/example/demo/repository/InterviewQuestionRepository.java
package com.example.demo.repository;

import com.example.demo.entity.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, Long> {
    List<InterviewQuestion> findBySessionId(Long sessionId);
    
    List<InterviewQuestion> findByCategoryAndDifficulty(String category, String difficulty);
}