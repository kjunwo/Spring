package com.dw.jdbcapp.service;

import com.dw.jdbcapp.dto.OrderRequestDTO;
import com.dw.jdbcapp.exception.InvalidRequestException;
import com.dw.jdbcapp.model.Order;
import com.dw.jdbcapp.model.OrderDetail;
import com.dw.jdbcapp.repository.Jdbc.OrderJdbcRepository;
import com.dw.jdbcapp.repository.iface.OrderDetailRepository;
import com.dw.jdbcapp.repository.iface.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.ClientInfoStatus;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    @Qualifier("orderTemplateRepository")
    OrderRepository orderRepository;
    @Autowired
    @Qualifier("orderDetailTemplateRepository")
    OrderDetailRepository orderDetailRepository;

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }
    public Order getOrderByNumber(String number) {
        return orderRepository.getOrderByNumber(number);
    }
    public List<Order> getOrderProductNumber (String number, String id) {
        List<Order> order = orderRepository.getOrderProductNumber (number, id);
        if (order.isEmpty()) {
            throw new InvalidRequestException("제품번호 또는 주문번호가 존재하지 않습니다: " + number + " ," + id);
        }
        return order;
    }

    public OrderRequestDTO saveOrder(OrderRequestDTO orderRequestDTO) {
        // 1. DTO에서 주문정보를 꺼내 주문테이블에 insert
        orderRepository.saveOrder(orderRequestDTO.toOrder());
        // 2. DTO에서 주문세부정보를 꺼내 주문세부테이블에 insert. 반복문필요
        for (OrderDetail data : orderRequestDTO.getOrderDetails()) {
            orderDetailRepository.saveOrderDetail(data);
        }
        return orderRequestDTO;
    }
    public List<Order> updateOrderWithShippingDate(String id, String date) {
        return orderRepository.updateOrderWithShippingDate(id, date);

    }
}

