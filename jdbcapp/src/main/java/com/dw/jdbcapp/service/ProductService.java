package com.dw.jdbcapp.service;

import com.dw.jdbcapp.exception.InvalidRequestException;
import com.dw.jdbcapp.model.Product;
import com.dw.jdbcapp.repository.Jdbc.ProductJdbcRepository;
import com.dw.jdbcapp.repository.iface.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    @Qualifier("productTemplateRepository")
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }
    public Product getProductById(int number) {
        if (number < 0) {
            throw new InvalidRequestException("존재하지 않는 제품번호: " + number);

        }
        return productRepository.getProductById(number);
    }
    public Product saveProduct (Product product) {
        return productRepository.saveProduct(product);
    }
    public List<Product> saveProductList (List<Product> productsList) {
        for (Product data : productsList) {
            productRepository.saveProduct(data);
    }
        return productsList;
    }

    public Product updateProduct (Product product) {
        return productRepository.updateProduct(product);
    }

    public int deleteProduct(int id) {
        return productRepository.deleteProduct(id);
    }
}
