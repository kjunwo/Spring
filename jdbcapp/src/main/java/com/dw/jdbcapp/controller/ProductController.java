package com.dw.jdbcapp.controller;

import com.dw.jdbcapp.model.Department;
import com.dw.jdbcapp.model.Product;
import com.dw.jdbcapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/find-all-products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(),
                HttpStatus.CREATED);
    }
    @GetMapping("/products/{productNumber}")
    public ResponseEntity<Product> getProductById(@PathVariable int productNumber) {
        return new ResponseEntity<>(productService.getProductById(productNumber),
                HttpStatus.CREATED);
    }

    @PostMapping("/post/product")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.saveProduct(product),
                HttpStatus.CREATED);
    }

    @PostMapping("/post/productlist")
    public ResponseEntity<List<Product>> saveProductList
            (@RequestBody List<Product> productList) {
        return new ResponseEntity<>(productService.saveProductList(productList),
                HttpStatus.CREATED);
    }
    @PutMapping("/put/product")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.updateProduct(product),
                HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/product")
    public ResponseEntity<String> deleteProduct(@RequestParam int id) {
        return new ResponseEntity<>("제품번호: " + productService.deleteProduct(id) + " 삭제됨",
                HttpStatus.CREATED);
    }
    @PutMapping("/products/update")
    public ResponseEntity<String> updateProductWithStock(@RequestParam int id,@RequestParam int stock) {
        return new ResponseEntity<>(productService.updateProductWithStock(id, stock), HttpStatus.OK);
    }
    @GetMapping("/products/name/{name}")
    public ResponseEntity<List<Product>> getProductByProductName(@PathVariable String name) {
        return new ResponseEntity<>(productService.getProductByProductName(name), HttpStatus.OK);
    }
}
