package com.microsoft.shinyay.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthCheckController {

    @Value("${spring.ai.azure.openai.endpoint:NOT_SET}")
    private String endpoint;

    @Value("${spring.ai.azure.openai.chat.options.deployment-name:NOT_SET}")
    private String deploymentName;

    @Value("${spring.ai.azure.openai.api-key:NOT_SET}")
    private String apiKey;

    @GetMapping("/health")
    public Map<String, String> healthCheck() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("endpoint", endpoint);
        status.put("deploymentName", deploymentName);
        // APIキーは最初の4文字だけ表示（セキュリティのため）
        status.put("apiKeyStatus", apiKey.equals("NOT_SET") ? "NOT_SET"
                : "SET (first 4 chars: " + apiKey.substring(0, Math.min(4, apiKey.length())) + "...)");
        return status;
    }
}
