package com.yash.learn_spring_ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AskAiAdvisorService{

    @Autowired
    private ChatClient chatClient;

    // VectorStoreChatMemoryAdvisor vectorStoreChatMemoryAdvisor;

    @Autowired
    private VectorStore vectorStore;

    public String askAiWithAdvisor(String prompt, String userId){

        System.out.println("Enter in AskAiAdvisorService");

        String systemPrompt = """
                You are helpful AI assistant called Nakku. Greet the user with your name and their name if you know.
                Always answer in a friendly manner.
                """;

        String response= chatClient.prompt().
            user(prompt).
            system(systemPrompt).
            advisors(
                    VectorStoreChatMemoryAdvisor.builder(vectorStore)
                    .conversationId(userId)   // Using userId as conversationId to maintain separate memory for each user
                    .defaultTopK(4)
                    .build()   
            ).
            call().
            content();

        System.out.println("Advisor AI Response: " + response);
        return response;
    }
}