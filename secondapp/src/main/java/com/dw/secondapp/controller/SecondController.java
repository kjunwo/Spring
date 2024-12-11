package com.dw.secondapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecondController {
    @GetMapping("/안녕")
    public String hello() {
        return "안녕하세요!";
    }
}
