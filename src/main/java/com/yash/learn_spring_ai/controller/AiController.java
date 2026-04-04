package com.yash.learn_spring_ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yash.learn_spring_ai.service.AiService;

@RestController
public class AiController {

    @Autowired
    private AiService aiService;

    @GetMapping("/prompt")
    public String getResponse(){

        String response = aiService.getAiResponse("India");
        System.out.println("AI Response: " + response);
        return response;
    }

    @GetMapping("/embed")
    public void getEmbeddings(){
        var resp= aiService.getEmbeddings("Naksh is a good boy, He has sexy Ass");
        System.out.println("Embed Response:" + resp);

        for(float f: resp){
            System.out.println(f);
        }
    }

    @GetMapping("/store-embedding")
    public String storeEmbedding(){
        aiService.storeEmbedding("This is sample test");
        return "Embedding stored successfully!";
    }
}
