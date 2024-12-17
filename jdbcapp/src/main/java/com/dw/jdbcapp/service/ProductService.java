package com.dw.jdbcapp.service;

import com.dw.jdbcapp.model.Product;
import com.dw.jdbcapp.repository.Jdbc.ProductJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductJdbcRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }
    public Product getProductByNumber(String number) {
        return productRepository.getProductByNumber(number);
    }
    public Product saveProduct (Product product) {
        return productRepository.saveProduct(product);
    }
    public List<Product> saveProductList (List<Product> productsList) {
        for (Product data : productsList) {
            productRepository.saveProductList(data);
        }
        return productsList;
    }
    public Product updateProduct (Product product) {
        return productRepository.updateProduct(product);
    }
}
