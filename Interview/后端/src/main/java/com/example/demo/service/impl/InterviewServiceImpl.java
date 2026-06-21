package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.dto.*;
import com.example.demo.repository.*;
import com.example.demo.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class InterviewServiceImpl implements InterviewService {

    private final InterviewSessionRepository sessionRepository;
    private final InterviewQuestionRepository questionRepository;
    private final UserAnswerRepository answerRepository;

    public InterviewServiceImpl(
        InterviewSessionRepository sessionRepository,
        InterviewQuestionRepository questionRepository,
        UserAnswerRepository answerRepository
    ) {
        this.sessionRepository = sessionRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public InterviewSession createInterviewSession(Long userId, String position, String difficulty) {
        InterviewSession session = new InterviewSession();
        session.setUserId(userId);
        session.setPosition(position);
        session.setDifficulty(difficulty);
        session.setStartTime(LocalDateTime.now());
        session.setStatus(SessionStatus.IN_PROGRESS);
        
        return sessionRepository.save(session);
    }

    @Override
    public void endInterviewSession(Long sessionId) {
        InterviewSession session = sessionRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("会话不存在"));
        
        session.setEndTime(LocalDateTime.now());
        session.setStatus(SessionStatus.COMPLETED);
        sessionRepository.save(session);
    }

    @Override
    public List<InterviewSession> getInterviewHistory(Long userId, int page, int size) {
        return sessionRepository.findByUserIdOrderByStartTimeDesc(userId);
    }

    @Override
    public List<InterviewQuestion> initializeQuestions(Long sessionId, String category, String difficulty, int count) {
        throw new UnsupportedOperationException("数据库题库出题已停用，请改用 /api/ai/generate-questions");
    }

    @Override
    public List<InterviewQuestion> getQuestionsBySession(Long sessionId) {
        return questionRepository.findBySessionId(sessionId);
    }

    @Override
    public InterviewQuestion getNextQuestion(Long sessionId) {
        List<InterviewQuestion> questions = getQuestionsBySession(sessionId);
        if (questions == null || questions.isEmpty()) {
            return null;
        }
        
        List<Long> answeredQuestionIds = answerRepository.findQuestionIdsBySessionId(sessionId);
        if (answeredQuestionIds == null) {
            answeredQuestionIds = new ArrayList<>();
        }
        
        for (InterviewQuestion q : questions) {
            if (!answeredQuestionIds.contains(q.getId())) {
                return q;
            }
        }
        return null;
    }

    @Override
    public UserAnswer saveUserAnswer(Long sessionId, Long questionId, String answerText, Long userId) {
        UserAnswer answer = new UserAnswer();
        answer.setSessionId(sessionId);
        answer.setQuestionId(questionId);
        answer.setUserId(userId);
        answer.setAnswerText(answerText);
        answer.setAnswerTime(LocalDateTime.now());
        
        return answerRepository.save(answer);
    }

    @Override
    public void updateAnswerWithFeedback(Long answerId, AnswerFeedback feedback) {
        UserAnswer answer = answerRepository.findById(answerId)
            .orElseThrow(() -> new IllegalArgumentException("答案不存在"));
        
        answer.setScore(feedback.getTotalScore());
        answer.setFeedback(feedback.getDetailedFeedback());
        answer.setAnalysisTime(LocalDateTime.now());
        
        answerRepository.save(answer);
    }

    @Override
    public InterviewReport generateInterviewReport(Long sessionId) {
        InterviewSession session = sessionRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("会话不存在"));
        
        List<UserAnswer> answers = answerRepository.findBySessionId(sessionId);
        if (answers == null) {
            answers = new ArrayList<>();
        }
        
        double overallScore = calculateOverallScore(answers);
        
        InterviewReport report = new InterviewReport();
        report.setSessionId(sessionId);
        report.setPosition(session.getPosition());
        report.setStartTime(session.getStartTime());
        report.setEndTime(session.getEndTime());
        report.setTotalScore(overallScore);
        report.setAnswers(answers);
        
        return report;
    }

    @Override
    public double calculateOverallScore(List<UserAnswer> answers) {
        if (answers == null || answers.isEmpty()) {
            return 0;
        }
        
        double total = 0;
        int count = 0;
        for (UserAnswer a : answers) {
            if (a.getScore() != null) {
                total += a.getScore();
                count++;
            }
        }
        return count > 0 ? total / count : 0;
    }
}
