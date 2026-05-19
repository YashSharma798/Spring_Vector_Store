package com.yash.learn_spring_ai.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class CarPriceTool {

    @Tool(name="Car Price Tool", description="Provides the price of a car model")
    public String getCarPrice(String carModel){

        switch(carModel.toLowerCase()){
            case "honda civic":
                return "The price of Honda Civic is around $20,000.";
            case "toyota camry":
                return "The price of Toyota Camry is around $25,000.";
            case "tesla model 3":
                return "The price of Tesla Model 3 is around $35,000.";
            default:
                return "Sorry, I don't have information about the price of that car model.";
        }
    }
    
}
