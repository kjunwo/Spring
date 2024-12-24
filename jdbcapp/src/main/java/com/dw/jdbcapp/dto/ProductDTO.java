package com.dw.jdbcapp.dto;

import com.dw.jdbcapp.model.Product;

public class ProductDTO {
    private int productId;
    private String productName;
    private double unitPrice;
    private int stock;
    private double stockValue;

    public ProductDTO() {
    }

    public ProductDTO(int productId, String productName, double unitPrice, int stock) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.stock = stock;
    }

    // 아래 fromProduct()와 동일한 기능을 가진 생성자를 만들 수 있음
    public ProductDTO(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.unitPrice = product.getUnitPrice();
        this.stock =  product.getStock();
        this.stockValue = product.getUnitPrice() * product.getStock();
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getStockValue() {
        return stockValue;
    }

    public void setStockValue(double stockValue) {
        this.stockValue = stockValue;
    }

    public Product toProduct() {
        Product product = new Product();
        product.setProductId(this.productId);
        product.setProductName(this.productName);
        product.setUnitPrice(this.unitPrice);
        product.setStock(this.stock);

        return product;
    }

    public static ProductDTO fromProduct(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(productDTO.getProductId());
        productDTO.setProductName(productDTO.getProductName());
        productDTO.setUnitPrice(productDTO.getUnitPrice());
        productDTO.setStock(product.getStock());
        productDTO.setStockValue(product.getUnitPrice() * product.getStock());
        return productDTO;
    }
}
