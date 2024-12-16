package com.dw.jdbcapp.controller;

import com.dw.jdbcapp.model.Department;
import com.dw.jdbcapp.model.Product;
import com.dw.jdbcapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/find-all-products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    @GetMapping("/api/products/{productNumber}")
    public Product getProductByNumber(@PathVariable String productNumber) {
        return productService.getProductByNumber(productNumber);
    }

    @PostMapping("api/post/product")
    public Product saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PostMapping("api/post/productlist")
    public List<Product> saveProductList
            (@RequestBody List<Product> productList) {
        return productService.saveProductList(productList);
    }
    @PutMapping("api/put/product")
    public Product updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }

}
