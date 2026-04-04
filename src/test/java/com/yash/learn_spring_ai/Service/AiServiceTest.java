package com.yash.learn_spring_ai.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yash.learn_spring_ai.service.AiService;

@SpringBootTest
public class AiServiceTest {

    @Autowired
    private AiService aiService;

    @Test
    public void testGetAiResponse() {
        String userInput = "What is the weather like today?";
        String response = aiService.getAiResponse(userInput);
        System.out.println("AI Response: " + response);
    }

    @Test
    public void testAskAi() {
        String userInput = "What is the capital of France?";
        String response = aiService.askAi(userInput);
        System.out.println("AI Response: " + response);
    }

}
