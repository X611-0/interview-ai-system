package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class AnswerFeedback {
    private double score;
    private double totalScore;
    private String detailedFeedback;
    private String summary;
    private List<FeedbackDimension> dimensions = new ArrayList<>();
    private List<String> suggestions = new ArrayList<>();

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
        this.totalScore = score;
    }

    public double getTotalScore() {
        return totalScore > 0 ? totalScore : score;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
        this.score = totalScore;
    }

    public String getDetailedFeedback() {
        return detailedFeedback;
    }

    public void setDetailedFeedback(String detailedFeedback) {
        this.detailedFeedback = detailedFeedback;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<FeedbackDimension> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<FeedbackDimension> dimensions) {
        this.dimensions = dimensions;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public static class FeedbackDimension {
        private String name;
        private double score;
        private String feedback;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }
    }
}
