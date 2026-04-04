package com.yash.learn_spring_ai.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiService {
    
    @Autowired
    private ChatClient chatClient;

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private VectorStore VectorStore;

    public float[] getEmbeddings(String input){
        var embeddings = embeddingModel.embed(input);
        System.out.println("Embeddings: " + embeddings);
        return embeddings;
    }

    public void storeEmbedding(String text){
        System.out.println("Start storing embedding for text: " + text);
        Document document = new Document(text);
        VectorStore.add(List.of(document));

        System.out.println("End storing embedding for text: " + text);
    }

    public String getAiResponse(String topic) {

        // String userInput = "What is the capital of France?";
        // return chatClient.prompt(userInput).call().content();

        String userInput = """
                You are a helpful assistant. Please provide a concise answer to the following question:
                Give me the list of states of following country: {topic}
            """;

        PromptTemplate promptTemplate = new PromptTemplate(userInput);

        String renderedPrompt = promptTemplate.render(Map.of("topic", topic));

        System.out.println("Rendered Prompt: " + renderedPrompt);

        var chatClientResponse = chatClient.prompt().
            user(renderedPrompt).
            call().
            chatClientResponse();


        System.out.println("Chat Client Response: " + chatClientResponse);

        return chatClient.prompt().
            user(renderedPrompt).
            advisors( new SimpleLoggerAdvisor()).           // Adding a simple logger advisor to log the conversation
            call().
            content();
    }
}
