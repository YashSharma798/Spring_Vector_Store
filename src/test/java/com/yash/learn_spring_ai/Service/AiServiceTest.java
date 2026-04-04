package com.yash.learn_spring_ai.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yash.learn_spring_ai.service.AiService;

public class AiServiceTest {

    @Autowired
    private AiService aiService;

    @Test
    public void testGetAiResponse() {
        String userInput = "What is the weather like today?";
        String response = aiService.getAiResponse(userInput);
        System.out.println("AI Response: " + response);
    }

}
