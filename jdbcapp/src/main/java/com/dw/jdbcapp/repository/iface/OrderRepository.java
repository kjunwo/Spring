package com.dw.jdbcapp.repository.iface;

import com.dw.jdbcapp.model.Order;

import java.util.List;
import java.util.Map;

public interface OrderRepository {
    List<Order> getAllOrders();
    Order getOrderByNumber(String number);
    List<Order> getOrderProductNumber(String number, String id);
    int saveOrder(Order order);
    String updateOrderWithShippingDate(String id, String date);
    List<Map<String, Integer>> getTopCitiesByTotalOrderAmount(int limit);
    List<Map<String, Object>> getOrderCountByYearForCity(String city);
}
