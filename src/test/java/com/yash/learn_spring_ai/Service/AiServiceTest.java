package com.yash.learn_spring_ai.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yash.learn_spring_ai.service.AiService;
import com.yash.learn_spring_ai.service.AskAiAdvisorService;
import com.yash.learn_spring_ai.service.CarPriceService;

@SpringBootTest
public class AiServiceTest {

    @Autowired
    private AiService aiService;
    
    @Autowired
    private AskAiAdvisorService AskAiAdvisorService;

    private CarPriceService carPriceService;

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

    @Test
    public void askAiFromAdvisor(){
        String response = AskAiAdvisorService.askAiWithAdvisor("what is my name?", "yash123");
        System.out.println("AI Response: " + response);
    }

    @Test
    public void carPriceToolTest(){

        String result= carPriceService.getCarPrice("Honda Civic");
        System.out.println("Car Price: " + result);
    }

}
