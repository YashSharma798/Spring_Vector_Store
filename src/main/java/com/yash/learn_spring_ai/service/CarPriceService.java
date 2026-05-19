package com.yash.learn_spring_ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.learn_spring_ai.tools.CarPriceTool;

@Service
public class CarPriceService {

    @Autowired
    private ChatClient chatClient;
    
    public String getCarPrice(String carModel){

        var price = chatClient.prompt()
            .system("You are a helpful assistant that provides the price of cars.")
            .tools(new CarPriceTool())
            .user("What is the price of " + carModel + "?")
            .call()
            .content();

        return price;

    }
}
