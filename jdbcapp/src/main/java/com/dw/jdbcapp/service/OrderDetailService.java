package com.dw.jdbcapp.service;

import com.dw.jdbcapp.model.OrderDetail;
import com.dw.jdbcapp.repository.Jdbc.OrderDetailJdbcRepository;
import com.dw.jdbcapp.repository.iface.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    @Qualifier("OrderDetailTemplateRepository")
    OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.getAllOrderDetails();
    }
}
