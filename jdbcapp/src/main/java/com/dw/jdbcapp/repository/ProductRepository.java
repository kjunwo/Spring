package com.dw.jdbcapp.repository;

import com.dw.jdbcapp.model.Department;
import com.dw.jdbcapp.model.Employee;
import com.dw.jdbcapp.model.Product;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "select * from 제품";
        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("제품번호"));
                product.setProductName(resultSet.getString("제품명"));
                product.setPackageUnit(resultSet.getString("포장단위"));
                product.setUnitPrice(resultSet.getInt("단가"));
                product.setStock(resultSet.getInt("재고"));

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public Product getProductByNumber(String number) {
        Product product = new Product();
        String query = "select * from 제품 where 제품번호 = ?";
        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = connection.prepareStatement(query)
        ){
            pstmt.setString(1, number);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    product.setProductId(resultSet.getInt("제품번호"));
                    product.setProductName(resultSet.getString("제품명"));
                    product.setPackageUnit(resultSet.getString("포장단위"));
                    product.setUnitPrice(resultSet.getInt("단가"));
                    product.setStock(resultSet.getInt("재고"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
}
