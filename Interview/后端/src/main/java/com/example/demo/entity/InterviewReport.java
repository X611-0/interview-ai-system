package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

public class InterviewReport {
    private Long sessionId;
    private String position;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double totalScore;
    private String summaryFeedback;
    private List<UserAnswer> answers;
    
    public Long getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public double getTotalScore() {
        return totalScore;
    }
    
    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }
    
    public String getSummaryFeedback() {
        return summaryFeedback;
    }
    
    public void setSummaryFeedback(String summaryFeedback) {
        this.summaryFeedback = summaryFeedback;
    }
    
    public List<UserAnswer> getAnswers() {
        return answers;
    }
    
    public void setAnswers(List<UserAnswer> answers) {
        this.answers = answers;
    }
}
