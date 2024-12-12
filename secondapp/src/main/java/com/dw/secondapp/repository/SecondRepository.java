package com.dw.secondapp.repository;

import org.springframework.stereotype.Repository;

@Repository
public class SecondRepository {
    public String hello() {
        return "내 오늘 저녁은 짜파게티";
    }
}
