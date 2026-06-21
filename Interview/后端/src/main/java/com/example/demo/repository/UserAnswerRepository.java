// src/main/java/com/example/demo/repository/UserAnswerRepository.java
package com.example.demo.repository;

import com.example.demo.entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
    List<UserAnswer> findBySessionId(Long sessionId);

    @Query("SELECT ua.questionId FROM UserAnswer ua WHERE ua.sessionId = :sessionId")
    List<Long> findQuestionIdsBySessionId(@Param("sessionId") Long sessionId);

    Optional<UserAnswer> findBySessionIdAndQuestionId(Long sessionId, Long questionId);
}