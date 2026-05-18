package com.yash.learn_spring_ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yash.learn_spring_ai.service.AiPdfReaderService;
import com.yash.learn_spring_ai.service.AiService;
import com.yash.learn_spring_ai.service.AskAiAdvisorService;

@RestController
public class AiController {

    @Autowired
    private AiService aiService;

    @Autowired
    private AiPdfReaderService AiPdfReaderService;

    @Autowired
    private AskAiAdvisorService AskAiAdvisorService;

    @GetMapping("/prompt")
    public String getResponse(){

        String response = aiService.getAiResponse("India");
        System.out.println("AI Response: " + response);
        return response;
    }

    @GetMapping("/embed")
    public void getEmbeddings(){
        var resp= aiService.getEmbeddings("Naksh is a good boy, He has sexy Kamar");
        System.out.println("Embed Response:" + resp);

        for(float f: resp){
            System.out.println(f);
        }
    }

    @GetMapping("/store-embedding")
    public String storeEmbedding(){
        aiService.storeEmbedding();
        return "Embedding stored successfully!";
    }

    @GetMapping("/ask")
    public String askAi(){
        String response = aiService.askAi("What is the capital of France?");
        System.out.println("AI Response: " + response);
        return response;
    }

    @GetMapping("/ingest-pdf")
    public String ingestPdf(){
        String response = AiPdfReaderService.ingestPdfToVectorStore();
        System.out.println("AI Response: " + response);
        return response;
    }

    @GetMapping("/ask-ai-advisor")
    public String askAiFromAdvisor(){
        String response = AskAiAdvisorService.askAiWithAdvisor("What is the capital of India?", "yash123");
        System.out.println("AI Response: " + response);
        return response;
    }
}
