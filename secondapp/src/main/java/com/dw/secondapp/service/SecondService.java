package com.dw.secondapp.service;

import com.dw.secondapp.repository.SecondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecondService {
    @Autowired
    SecondRepository secondRepository;

    public String hello() {
        return secondRepository.hello();
    }
}
