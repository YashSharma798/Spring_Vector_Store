package com.yash.learn_spring_ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AskAiAdvisorService {

    @Autowired
    private ChatClient chatClient;

    // VectorStoreChatMemoryAdvisor vectorStoreChatMemoryAdvisor;

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private ChatMemory chatMemory;

    public String askAiWithAdvisor(String prompt, String userId){

        // ChatMemory chatMemory;

        System.out.println("Enter in AskAiAdvisorService");

        String systemPrompt = """
                You are helpful AI assistant called Nakku. Greet the user with your name and their name if you know.
                Always answer in a friendly manner.
            """;

        String response= chatClient.prompt().
            user(prompt).
            system(systemPrompt).
            advisors(

                    // Short term memory advisor that uses the conversation history of the current
                    // session to provide context for generating responses.

                    MessageChatMemoryAdvisor.builder(chatMemory)
                    .conversationId(userId)
                    .build(),


                    // Long term memory advisor that uses a vector store to maintain conversation history and context across interactions with the user.
                    // Its uses conversationId to maintain separate memory for each user, allowing it to provide personalized responses based on past interactions.

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