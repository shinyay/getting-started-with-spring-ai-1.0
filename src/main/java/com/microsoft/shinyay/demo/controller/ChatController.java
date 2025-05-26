package com.microsoft.shinyay.demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping("/simple")
    public String simpleChat(@RequestBody String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    @PostMapping("/detailed")
    public String detailedChat(@RequestBody ChatRequest chatRequest) {
        Prompt prompt = chatRequest.toPrompt();
        return chatClient.call(prompt).content();
    }

    public static class ChatRequest {
        private String userMessage;
        private String systemMessage;
        private Float temperature;

        // Getters and setters
        public String getUserMessage() {
            return userMessage;
        }

        public void setUserMessage(String userMessage) {
            this.userMessage = userMessage;
        }

        public String getSystemMessage() {
            return systemMessage;
        }

        public void setSystemMessage(String systemMessage) {
            this.systemMessage = systemMessage;
        }

        public Float getTemperature() {
            return temperature;
        }

        public void setTemperature(Float temperature) {
            this.temperature = temperature;
        }

        public Prompt toPrompt() {
            var promptBuilder = org.springframework.ai.chat.prompt.PromptBuilder.builder();
            
            if (systemMessage != null && !systemMessage.isEmpty()) {
                promptBuilder.system(systemMessage);
            }
            
            promptBuilder.user(userMessage);

            var options = org.springframework.ai.chat.prompt.PromptOptions.builder();
            if (temperature != null) {
                options.withTemperature(temperature);
            }
            
            return promptBuilder.options(options.build()).build();
        }
    }
}