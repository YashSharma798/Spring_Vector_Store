package com.yash.learn_spring_ai.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
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

    public void storeEmbedding(){

        System.out.println("Start storing embedding for document");

        List<Document> document = List.of(

            new Document(
                "The Maruti Suzuki Dzire 2026 continues to dominate the compact sedan segment with high fuel efficiency and affordability.",
                Map.of("brand", "Maruti Suzuki", "model", "Dzire", "segment", "Compact Sedan", "fuelType", "Petrol/CNG")
            ),

            new Document(
                "The Honda City remains a premium mid-size sedan choice in India, known for its refined engine and spacious cabin.",
                Map.of("brand", "Honda", "model", "City", "segment", "Mid-size Sedan", "fuelType", "Petrol/Hybrid")
            ),

            new Document(
                "The Hyundai Verna 2026 stands out with its modern design, turbo engine options, and advanced features.",
                Map.of("brand", "Hyundai", "model", "Verna", "segment", "Mid-size Sedan", "fuelType", "Petrol", "feature", "Turbo Engine")
            ),

            new Document(
                "The Skoda Slavia offers strong build quality and performance-focused driving dynamics in the Indian sedan market.",
                Map.of("brand", "Skoda", "model", "Slavia", "segment", "Mid-size Sedan", "fuelType", "Petrol", "strength", "Performance")
            ),

            new Document(
                "The Volkswagen Virtus is gaining popularity for its German engineering, safety features, and powerful TSI engines.",
                Map.of("brand", "Volkswagen", "model", "Virtus", "segment", "Mid-size Sedan", "fuelType", "Petrol", "engineType", "TSI")
            ),
              new Document(
                "The Tata Tigor 2026 is a budget-friendly compact sedan offering strong safety ratings and electric variant options.",
                Map.of("brand", "Tata", "model", "Tigor", "segment", "Compact Sedan", "fuelType", "Petrol/CNG/Electric", "highlight", "Safety")
            ),
            new Document(
                "The Hyundai Aura continues to attract buyers in the compact sedan segment with its feature-rich cabin and smooth city drive.",
                Map.of("brand", "Hyundai", "model", "Aura", "segment", "Compact Sedan", "fuelType", "Petrol/CNG", "usage", "City Driving")
            ),
            new Document(
                "The Toyota Camry Hybrid remains a luxury sedan option in India with excellent fuel efficiency and premium comfort.",
                Map.of("brand", "Toyota", "model", "Camry", "segment", "Luxury Sedan", "fuelType", "Hybrid", "comfort", "Premium")
            )

        );  

        VectorStore.add(document);

        System.out.println("End storing embedding for document");
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
            chatClientResponse();    // To get all info like, token usage, response time, etc.


        System.out.println("Chat Client Response: " + chatClientResponse);

        return chatClient.prompt().
            user(renderedPrompt).
            advisors( new SimpleLoggerAdvisor()).           // Adding a simple logger advisor to log the conversation
            call().
            content();
    }


    public String askAi(String text){
        
        System.out.println("Enter AskAI service");

        List<Document> relevantChunks = VectorStore.similaritySearch(SearchRequest.builder().
            query(text).
            topK(3).
            build()
        );

        String context = relevantChunks.stream()
            .map(Document:: getText)
            .collect(Collectors.joining("\n\n"));

        System.out.println("Relevant Chunks: " + relevantChunks);
        System.out.println("Context: " + context);

        PromptTemplate promptTemplate = new PromptTemplate("""
            You are a helpful assistant. Please provide a concise answer to the following question based on the provided context:

            Rules: 
            1) If the answer is not present in the context, say "I don't know".
            2) If the answer is present in the context, provide the answer without any additional information.
            3) Do not use any information that is not present in the context.

            Context: {context}

            Provide me answer from the above context for the following question: {text}
        """);

        String systemPrompt = promptTemplate.render(Map.of("context", context, "text", text));

        System.out.println("System Prompt: " + systemPrompt);


        String response=  chatClient.prompt()
            .system(systemPrompt)
            .call()
            .content();

        System.out.println("Exit AskAI service: " + response);
        
        return response;
    }
}
