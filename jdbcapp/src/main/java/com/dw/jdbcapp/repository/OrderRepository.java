package com.dw.jdbcapp.repository;

import com.dw.jdbcapp.model.Department;
import com.dw.jdbcapp.model.Order;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "select * from 부서";
        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId();
                order.setCustomerId();
                order.setEmployeeId();


                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
