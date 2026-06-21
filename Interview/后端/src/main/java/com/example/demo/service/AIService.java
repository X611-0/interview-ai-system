package com.example.demo.service;

import com.example.demo.dto.AnswerFeedback;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AIService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${qwen.api.base-url}")
    private String apiBaseUrl;

    @Value("${qwen.api.key}")
    private String apiKey;

    public AIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String callQwenApi(List<Map<String, Object>> messages, double temperature, int maxTokens) throws Exception {
        return callQwenApi(messages, temperature, maxTokens, "qwen-plus");
    }

    private String callQwenApi(List<Map<String, Object>> messages, double temperature, int maxTokens, String model) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        body.put("messages", messages);
        body.put("temperature", temperature);
        body.put("max_tokens", maxTokens);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(apiBaseUrl, entity, String.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("调用千问接口失败，状态码：" + response.getStatusCode());
        }

        JsonNode root = objectMapper.readTree(response.getBody());
        JsonNode choices = root.path("choices");
        if (!choices.isArray() || choices.isEmpty()) {
            throw new RuntimeException("千问返回数据格式异常");
        }
        return choices.get(0).path("message").path("content").asText();
    }

    private boolean isImageContent(String content) {
        return content != null && content.startsWith("data:image/") && content.contains(";base64,");
    }

    public List<Map<String, Object>> generateQuestions(String jdContent, String resumeContent, int count) throws Exception {
        String normalizedJd = jdContent == null ? "" : jdContent.trim();
        String normalizedResume = resumeContent == null ? "" : resumeContent.trim();
        int safeCount = Math.max(1, Math.min(count, 20));

        if (normalizedJd.isEmpty()) {
            throw new RuntimeException("JD 内容不能为空");
        }
        if (normalizedResume.isEmpty()) {
            throw new RuntimeException("简历内容不能为空");
        }
        if (normalizedJd.contains("[文件:") || normalizedJd.contains("[图片:")) {
            throw new RuntimeException("JD 必须提供可解析的正文内容，不能只上传文件名或图片名");
        }
        if (normalizedResume.contains("[文件:") || normalizedResume.contains("[图片:")) {
            throw new RuntimeException("简历必须提供可解析的正文内容，不能只上传文件名或图片名");
        }

        boolean jdIsImage = isImageContent(normalizedJd);
        boolean resumeIsImage = isImageContent(normalizedResume);

        StringBuilder instruction = new StringBuilder("我是一名求职者，正在准备面试。\n")
                .append("请严格基于我提供的 JD 与简历生成 ").append(safeCount)
                .append(" 道面试问题，必须体现岗位要求、候选人经历与项目背景，不允许输出通用模板题。问题类型包括：技术、行为、项目经验。\n\n")
                .append("请严格按照以下 JSON 格式返回，不要有其他内容：\n[")
                .append("  {\"id\": 1, \"question\": \"问题 1 内容\", \"category\": \"技术\"},")
                .append("  {\"id\": 2, \"question\": \"问题 2 内容\", \"category\": \"行为\"}")
                .append("]");

        List<Map<String, Object>> messages = new ArrayList<>();
        Map<String, Object> msg = new HashMap<>();
        msg.put("role", "user");

        String result;
        if (jdIsImage || resumeIsImage) {
            List<Map<String, Object>> content = new ArrayList<>();

            Map<String, Object> textPart = new HashMap<>();
            textPart.put("type", "text");
            StringBuilder textContent = new StringBuilder(instruction);
            if (!jdIsImage) {
                textContent.append("\n\n岗位 JD 文本：\n").append(normalizedJd);
            } else {
                textContent.append("\n\n岗位 JD 由图片提供，请直接识别图片内容。");
            }
            if (!resumeIsImage) {
                textContent.append("\n\n个人简历文本：\n").append(normalizedResume);
            } else {
                textContent.append("\n\n个人简历由图片提供，请直接识别图片内容。");
            }
            textPart.put("text", textContent.toString());
            content.add(textPart);

            if (jdIsImage) {
                Map<String, Object> jdImagePart = new HashMap<>();
                jdImagePart.put("type", "image_url");
                Map<String, Object> jdImageUrl = new HashMap<>();
                jdImageUrl.put("url", normalizedJd);
                jdImagePart.put("image_url", jdImageUrl);
                content.add(jdImagePart);
            }

            if (resumeIsImage) {
                Map<String, Object> resumeImagePart = new HashMap<>();
                resumeImagePart.put("type", "image_url");
                Map<String, Object> resumeImageUrl = new HashMap<>();
                resumeImageUrl.put("url", normalizedResume);
                resumeImagePart.put("image_url", resumeImageUrl);
                content.add(resumeImagePart);
            }

            msg.put("content", content);
            messages.add(msg);
            result = callQwenApi(messages, 0.6, 2000, "qwen-vl-plus");
        } else {
            String textPrompt = instruction
                    .append("\n\n岗位 JD 内容：\n").append(normalizedJd)
                    .append("\n\n个人简历内容：\n").append(normalizedResume)
                    .toString();
            msg.put("content", textPrompt);
            messages.add(msg);
            result = callQwenApi(messages, 0.8, 2000, "qwen-plus");
        }

        // 从返回文本中提取 JSON 数组
        String jsonStr = result;
        int start = result.indexOf('[');
        int end = result.lastIndexOf(']');
        if (start >= 0 && end > start) {
            jsonStr = result.substring(start, end + 1);
        }

        JsonNode arrayNode = objectMapper.readTree(jsonStr);
        if (!arrayNode.isArray() || arrayNode.isEmpty()) {
            throw new RuntimeException("解析问题列表失败");
        }

        List<Map<String, Object>> list = new ArrayList<>();
        for (JsonNode node : arrayNode) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", node.path("id").asInt());
            item.put("question", node.path("question").asText());
            item.put("category", node.path("category").asText());
            list.add(item);
        }
        return list;
    }

    public AnswerFeedback analyzeAnswer(String question, String answer) throws Exception {
        StringBuilder content = new StringBuilder();
        content.append("面试问题：").append(question).append("\n\n")
                .append("应聘者回答：").append(answer).append("\n\n")
                .append("请作为资深面试官，对以上回答进行评分和反馈。请严格按照以下 JSON 格式返回：\n")
                .append("{\n")
                .append("  \"score\": 85,\n")
                .append("  \"dimensions\": [\n")
                .append("    {\"name\": \"技术正确性\", \"score\": 80, \"feedback\": \"具体反馈\"},\n")
                .append("    {\"name\": \"逻辑清晰度\", \"score\": 85, \"feedback\": \"具体反馈\"},\n")
                .append("    {\"name\": \"表达流畅度\", \"score\": 80, \"feedback\": \"具体反馈\"},\n")
                .append("    {\"name\": \"完整性\", \"score\": 75, \"feedback\": \"具体反馈\"}\n")
                .append("  ],\n")
                .append("  \"suggestions\": [\"建议 1\", \"建议 2\", \"建议 3\"],\n")
                .append("  \"summary\": \"总体评价\"\n")
                .append("}");

        List<Map<String, Object>> messages = new ArrayList<>();
        Map<String, Object> msg = new HashMap<>();
        msg.put("role", "user");
        msg.put("content", content.toString());
        messages.add(msg);

        String result = callQwenApi(messages, 0.2, 1000);

        // 提取 JSON 对象
        int start = result.indexOf('{');
        int end = result.lastIndexOf('}');
        if (start < 0 || end <= start) {
            throw new RuntimeException("解析反馈失败");
        }
        String jsonStr = result.substring(start, end + 1);

        JsonNode node = objectMapper.readTree(jsonStr);
        if (!node.hasNonNull("score")) {
            throw new RuntimeException("千问返回的评分字段缺失");
        }

        String summary = node.path("summary").asText("").trim();
        if (summary.isEmpty()) {
            throw new RuntimeException("千问返回的总结字段缺失");
        }

        JsonNode dimensionsNode = node.path("dimensions");
        if (!dimensionsNode.isArray() || dimensionsNode.isEmpty()) {
            throw new RuntimeException("千问返回的维度评分缺失");
        }

        AnswerFeedback feedback = new AnswerFeedback();
        feedback.setScore(node.path("score").asDouble());
        feedback.setSummary(summary);
        feedback.setDetailedFeedback(node.toString());

        List<AnswerFeedback.FeedbackDimension> dimensions = new ArrayList<>();
        for (JsonNode dimensionNode : dimensionsNode) {
            AnswerFeedback.FeedbackDimension dimension = new AnswerFeedback.FeedbackDimension();
            dimension.setName(dimensionNode.path("name").asText());
            dimension.setScore(dimensionNode.path("score").asDouble(0));
            dimension.setFeedback(dimensionNode.path("feedback").asText());
            dimensions.add(dimension);
        }
        feedback.setDimensions(dimensions);

        List<String> suggestions = new ArrayList<>();
        JsonNode suggestionsNode = node.path("suggestions");
        if (suggestionsNode.isArray()) {
            for (JsonNode suggestionNode : suggestionsNode) {
                suggestions.add(suggestionNode.asText());
            }
        }
        feedback.setSuggestions(suggestions);
        return feedback;
    }
}
