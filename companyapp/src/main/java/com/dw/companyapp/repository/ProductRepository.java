package com.dw.companyapp.repository;

import com.dw.companyapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
@Query("select p from Product p where p.unitPrice < :price")
    List<Product> getProductsBelowPrice(Double price);
@Query("select p from Product p where p.productName like %:name%")
    List<Product> getProductByProductName(String name);
}
