package com.dw.secondapp.controller;

import com.dw.secondapp.service.SecondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecondController {
    @Autowired
    SecondService secondService;

    @GetMapping("/hello")
    public String hello() {
        return secondService.hello();
    }
}
