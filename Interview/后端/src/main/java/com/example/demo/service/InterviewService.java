package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.dto.AnswerFeedback;
import java.util.List;

public interface InterviewService {

    InterviewSession createInterviewSession(Long userId, String position, String difficulty);
    void endInterviewSession(Long sessionId);
    List<InterviewSession> getInterviewHistory(Long userId, int page, int size);
    
    List<InterviewQuestion> initializeQuestions(Long sessionId, String category, String difficulty, int count);
    List<InterviewQuestion> getQuestionsBySession(Long sessionId);
    InterviewQuestion getNextQuestion(Long sessionId);
    
    UserAnswer saveUserAnswer(Long sessionId, Long questionId, String answerText, Long userId);
    void updateAnswerWithFeedback(Long answerId, AnswerFeedback feedback);
    
    InterviewReport generateInterviewReport(Long sessionId);
    
    double calculateOverallScore(List<UserAnswer> answers);
}
