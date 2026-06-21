// src/main/java/com/example/demo/service/ErnieService.java
package com.example.demo.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ErnieService {

    private final RestTemplate restTemplate;
    private final ErnieAuthService authService;

    @Value("${ernie.api.url}")
    private String apiUrl;

    public ErnieService(RestTemplate restTemplate, ErnieAuthService authService) {
        this.restTemplate = restTemplate;
        this.authService = authService;
    }

    // 调用文心一言API分析面试回答
    public String analyzeInterviewAnswer(String question, String answer) {
        try {
            // 构建请求内容
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "ernie-bot");  // 使用文心大模型
            requestBody.put("temperature", 0.2);   // 低创造性，保证评分客观性

            // 设置对话消息
            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", buildPrompt(question, answer));

            requestBody.put("messages", Arrays.asList(userMessage));

            // 添加访问令牌到URL
            String requestUrl = apiUrl + "?access_token=" + authService.getAccessToken();

            // 发送请求
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(
                requestUrl,
                requestEntity,
                String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                // 解析API响应
                JSONObject result = JSONObject.parseObject(response.getBody());
                return extractResponseContent(result);
            } else {
                throw new RuntimeException("文心一言API调用失败: " + response.getBody());
            }
        } catch (Exception e) {
            throw new RuntimeException("调用文心一言API时发生错误", e);
        }
    }

    // 构建提示词
    private String buildPrompt(String question, String answer) {
        return String.format(
            "你是一位资深技术面试官。请对以下面试回答进行评分和反馈：\n" +
            "问题：%s\n" +
            "回答：%s\n\n" +
            "请按照以下维度评分（0-100分）并给出具体反馈：\n" +
            "1. 技术正确性（40分）\n" +
            "2. 逻辑清晰度（30分）\n" +
            "3. 表达流畅度（20分）\n" +
            "4. 完整性（10分）\n" +
            "5. 综合得分（总分）\n" +
            "6. 优点和改进建议",
            question, answer
        );
    }

    // 从响应中提取内容
    private String extractResponseContent(JSONObject response) {
        // 根据文心一言API实际返回结构调整
        if (response.containsKey("result")) {
            return response.getString("result");
        }
        return "抱歉，AI分析失败，请稍后再试。";
    }
}