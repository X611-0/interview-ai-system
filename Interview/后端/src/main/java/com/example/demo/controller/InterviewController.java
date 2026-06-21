package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.service.InterviewService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/interview")
public class InterviewController {
    @Autowired
    private InterviewService interviewService;

    @Autowired
    private UserService userService;

    @PostMapping("/start")
    public ResponseEntity<InterviewSession> startInterview(@RequestBody StartInterviewRequest request) {
        Long userId = getCurrentUserId();
        InterviewSession session = interviewService.createInterviewSession(
            userId,
            request.getPosition(),
            request.getDifficulty()
        );
        return ResponseEntity.ok(session);
    }

    @GetMapping("/questions")
    public ResponseEntity<List<InterviewQuestion>> getQuestions(
            @RequestParam Long sessionId,
            @RequestParam String category,
            @RequestParam String difficulty,
            @RequestParam(defaultValue = "5") int count) {
        throw new ResponseStatusException(HttpStatus.GONE, "旧题库出题接口已停用，请改用 /api/ai/generate-questions");
    }

    @PostMapping("/answer")
    public ResponseEntity<UserAnswer> submitAnswer(@RequestBody SubmitAnswerRequest request) {
        Long userId = getCurrentUserId();
        UserAnswer answer = interviewService.saveUserAnswer(
                request.getSessionId(),
                request.getQuestionId(),
                request.getAnswerText(),
                userId
        );
        return ResponseEntity.ok(answer);
    }

    @PostMapping("/finish/{sessionId}")
    public ResponseEntity<InterviewSession> finishInterview(@PathVariable Long sessionId) {
        interviewService.endInterviewSession(sessionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/report/{sessionId}")
    public ResponseEntity<InterviewReport> getReport(@PathVariable Long sessionId) {
        InterviewReport report = interviewService.generateInterviewReport(sessionId);
        return ResponseEntity.ok(report);
    }

    /**
     * 从 Spring Security 上下文中提取当前登录用户 ID
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "未登录");
        }
        Object principal = authentication.getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userService.findByUsername(username).getId();
    }
}
