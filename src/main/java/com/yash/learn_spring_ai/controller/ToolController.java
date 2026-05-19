package com.yash.learn_spring_ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.learn_spring_ai.service.CarPriceService;

@RestController
public class ToolController {

    @Autowired
    private CarPriceService carPriceService;
    
    @GetMapping("/car-price")
    public String getCarPrice(){

        String result= carPriceService.getCarPrice("Honda Civic");
        return result;
    }
}
