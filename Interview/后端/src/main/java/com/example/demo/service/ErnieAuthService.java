// src/main/java/com/example/demo/service/ErnieAuthService.java
package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class ErnieAuthService {

    private final RestTemplate restTemplate;

    @Value("${ernie.api.key}")
    private String apiKey;

    @Value("${ernie.secret.key}")
    private String secretKey;

    @Value("${ernie.token.url}")
    private String tokenUrl;

    private String accessToken;
    private long tokenExpireTime;

    public ErnieAuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 获取访问令牌（自动处理过期）
    public synchronized String getAccessToken() {
        // 检查令牌是否存在或已过期
        if (accessToken == null || System.currentTimeMillis() >= tokenExpireTime) {
            renewAccessToken();
        }
        return accessToken;
    }

    // 刷新访问令牌
    private void renewAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 构建请求参数
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "client_credentials");
        params.put("client_id", apiKey);
        params.put("client_secret", secretKey);

        // 发送请求
        ResponseEntity<String> response = restTemplate.postForEntity(
            tokenUrl,
            new HttpEntity<>(params, headers),
            String.class
        );

        // 解析响应获取令牌
        if (response.getStatusCode() == HttpStatus.OK) {
            String result = response.getBody();
            // 使用FastJSON解析
            Map<String, Object> resultMap = com.alibaba.fastjson2.JSONObject.parseObject(result);
            accessToken = (String) resultMap.get("access_token");
            // 计算过期时间（提前5分钟刷新）
            long expiresIn = Long.parseLong(resultMap.get("expires_in").toString());
            tokenExpireTime = System.currentTimeMillis() + (expiresIn - 300) * 1000;
        } else {
            throw new RuntimeException("获取文心一言访问令牌失败: " + response.getBody());
        }
    }
}