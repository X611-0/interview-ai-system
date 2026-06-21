// src/main/java/com/example/demo/repository/InterviewSessionRepository.java
package com.example.demo.repository;

import com.example.demo.entity.InterviewSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewSessionRepository extends JpaRepository<InterviewSession, Long> {
    List<InterviewSession> findByUserIdOrderByStartTimeDesc(Long userId);
    
    Page<InterviewSession> findByUserIdOrderByStartTimeDesc(Long userId, Pageable pageable);
}