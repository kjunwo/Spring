package com.dw.jdbcapp.repository.iface;

import com.dw.jdbcapp.model.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> getAllProducts();
    public Product getProductById(int number);
    public Product saveProduct(Product product);
    public Product updateProduct (Product product);
    public int deleteProduct(int id);
    String updateProductWithStock(int id, int stock);
}
