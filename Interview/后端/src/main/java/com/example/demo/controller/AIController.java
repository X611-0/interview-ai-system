package com.example.demo.controller;

import com.example.demo.dto.AnswerFeedback;
import com.example.demo.dto.ApiResponse;
import com.example.demo.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    @PostMapping("/generate-questions")
    public ApiResponse<List<Map<String, Object>>> generateQuestions(@RequestBody Map<String, Object> body) {
        try {
            String jdContent = (String) body.getOrDefault("jdContent", "");
            String resumeContent = (String) body.getOrDefault("resumeContent", "");
            int count = 5;
            Object countObj = body.get("count");
            if (countObj instanceof Number number) {
                count = number.intValue();
            }

            List<Map<String, Object>> questions = aiService.generateQuestions(jdContent, resumeContent, count);
            return ApiResponse.success(questions);
        } catch (Exception e) {
            return ApiResponse.error("生成问题失败: " + e.getMessage());
        }
    }

    @PostMapping("/analyze-answer")
    public ApiResponse<AnswerFeedback> analyzeAnswer(@RequestBody Map<String, String> body) {
        try {
            String question = body.getOrDefault("question", "");
            String answer = body.getOrDefault("answer", "");
            AnswerFeedback feedback = aiService.analyzeAnswer(question, answer);
            return ApiResponse.success(feedback);
        } catch (Exception e) {
            return ApiResponse.error("分析回答失败: " + e.getMessage());
        }
    }
}
