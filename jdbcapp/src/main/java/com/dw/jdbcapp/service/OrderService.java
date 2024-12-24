package com.dw.jdbcapp.service;

import com.dw.jdbcapp.dto.OrderRequestDTO;
import com.dw.jdbcapp.exception.InvalidRequestException;
import com.dw.jdbcapp.model.Order;
import com.dw.jdbcapp.model.OrderDetail;
import com.dw.jdbcapp.model.Product;
import com.dw.jdbcapp.repository.Jdbc.OrderJdbcRepository;
import com.dw.jdbcapp.repository.iface.OrderDetailRepository;
import com.dw.jdbcapp.repository.iface.OrderRepository;
import com.dw.jdbcapp.repository.iface.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.ClientInfoStatus;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    @Qualifier("orderTemplateRepository")
    OrderRepository orderRepository;
    @Autowired
    @Qualifier("orderDetailTemplateRepository")
    OrderDetailRepository orderDetailRepository;
    @Autowired
    @Qualifier("productTemplateRepository")
    ProductRepository productRepository;

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
    public String updateOrderWithShippingDate(String id, String date) {
        return orderRepository.updateOrderWithShippingDate(id, date);
    }
    public List<Map<String, Integer>> getTopCitiesByTotalOrderAmount(int limit) {
        return orderRepository.getTopCitiesByTotalOrderAmount(limit);
    }
    public List<Map<String, Integer>> getOrderCountByYearForCity(String city) {
        List<Map<String, Object>> mapList = orderRepository.getOrderCountByYearForCity(city);
        List<Map<String, Integer>> getOrderCountByYearForCity = new ArrayList<>();
        for (Map<String, Object> objectMap : mapList) {
            Map<String, Integer> stringIntegerMap = new HashMap<>();
            stringIntegerMap.put("주문년도",Integer.valueOf(objectMap.get("주문년도").toString()));
            stringIntegerMap.put("주문건수",Integer.valueOf(objectMap.get("주문건수").toString()));
            getOrderCountByYearForCity.add(stringIntegerMap);
        }
        return getOrderCountByYearForCity ;
    }

    // @Transactional은 선언된 메서드 수행도중 예외가 발생하면 이미 수행되었던
    // 동작을 모두 롤백(rollback=원상복귀)시키도록 명령하는 어노테이션임
    // 주문세부의 특정 제품의 재고가 부족해서 예외가 발생하면 전체 주문,주문세부의
    // 저장되었던 내용들은 모두 취소되고 롤백됨!!
    @Transactional // 예외가 발생하면 롤백하라는 의미 (한몸을 의미하기도 함)
    public OrderRequestDTO getsaveOrder(OrderRequestDTO orderRequestDTO) {
        orderRepository.saveOrder(orderRequestDTO.toOrder());
        for (OrderDetail data : orderRequestDTO.getOrderDetails()) {
            Product product = productRepository.getProductById(data.getProductId());
            if (data.getOrderQuantity() > product.getStock()) {
                throw new InvalidRequestException(
                        "요청하신 수량은 현재 재고를 초과합니다: " + product.getProductName() +
                                ", 현재 재고 " +  product.getStock());
            } else {
                orderDetailRepository.saveOrderDetail(data);
            }
        }
        return orderRequestDTO;
    }
}

