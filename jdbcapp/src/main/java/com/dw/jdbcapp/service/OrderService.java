package com.dw.jdbcapp.service;

import com.dw.jdbcapp.model.Order;
import com.dw.jdbcapp.repository.Jdbc.OrderJdbcRepository;
import com.dw.jdbcapp.repository.iface.OrderDetailRepository;
import com.dw.jdbcapp.repository.iface.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    @Qualifier("OrderTemplateRepository")
    OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }
    public Order getOrderByNumber(String number) {
        return orderRepository.getOrderByNumber(number);
    }
    public Order getOrderProductNumber (String number, String id) {
        return orderRepository.getOrderProductNumber (number, id);
    }
}

