package com.yash.learn_spring_ai.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
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

                    // This advisor is responsible for filtering out inappropriate content from the AI's responses. 
                    // It mathces the content of the response against a list of predefined keywords provide in the list 
                    // and if any of those keywords are present in the response, the advisor can modify or block the response.
                   
                    new SafeGuardAdvisor(List.of("sex", "masturbation")),

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
                    .build(), 
                    
                    QuestionAnswerAdvisor.builder(vectorStore)
                    .searchRequest(SearchRequest.builder().
                        filterExpression("file_name == 'Project.pdf'").build()
                    )
                    .build()
                    
            ).
            call().
            content();

        System.out.println("Advisor AI Response: " + response);
        return response;
    }
}