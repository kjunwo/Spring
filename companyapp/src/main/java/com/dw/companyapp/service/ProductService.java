package com.dw.companyapp.service;

import com.dw.companyapp.dto.ProductDTO;
import com.dw.companyapp.exception.ResourceNotFoundException;
import com.dw.companyapp.model.Product;
import com.dw.companyapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 과제 1-1 제품번호를 기준으로 제품 정보를 조회하는 API
    public Product getProductById(Long productNumber) {
        return productRepository.findById(productNumber)
                .orElseThrow(() -> new RuntimeException());
    }

    // 과제 2-1 제품테이블에 새로운 제품 1개를 추가하는 API
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // 과제 2-2 제품테이블에 여러 제품을 추가하는 API
    public List<Product> saveProductList(List<Product> productList) {
        return productRepository.saveAll(productList);
    }

    // 과제 2-4 제품테이블의 정보를 수정하는 API
    public Product updateProduct(Product product) {
        return productRepository.findById(product.getProductId())
                .map(product1 -> {
                    product1.setProductId(product.getProductId());
                    product1.setProductName(product.getProductName());
                    product1.setPkgUnit(product.getPkgUnit());
                    product1.setUnitPrice(product.getUnitPrice());
                    product1.setStock(product.getStock());
                    return productRepository.save(product1);
                })
                .orElseThrow(() -> new RuntimeException("찾을 수 없습니다.:" + product.getProductId()));
    }

    // 과제 2-5 제품테이블의 정보를 삭제하는 API
    public String deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return id + "삭제 되었습니다.";
                })
                .orElseThrow(() -> new RuntimeException(id + "해당 제품을 찾을 수 없습니다."));
    }


    // 과제 3-5 제품을 조회할 때 단가를 매개변수로 전달하고 해당 단가보다 싼 제품을 조회하는 API
    // 해당 단가보다 싼 제품이 없을 경우, "해당되는 제품이 없습니다"를 출력하는 예외처리
    public List<Product> getProductsBelowPrice(double price) {
        List<Product> products = productRepository.getProductsBelowPrice(price);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("해당되는 제품이 없습니다.");
        }
        return products;
    }


    // 과제 4-8 제품번호와 재고를 매개변수로 해당 제품의 재고를 수정하는 API
    public String updateProductWithStock(Long id, int stock) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + "해당 제품을 찾을 수 없습니다."));
        product.setStock(stock);
        productRepository.save(product);
        return "재고 수정을 완료 하였습니다. ";
    }

    // 과제 4-9 제품명의 일부를 매개변수로 해당 문자열을 포함하는 제품들을 조회하는 API
    public List<Product> getProductByProductName(String name) {
        List<Product> products = productRepository.getProductByProductName(name);
        if (products.isEmpty()){
            throw new ResourceNotFoundException("해당되는 제품이 없습니다.");
        }
        return products;
    }

    // 과제 4-10 ProductDTO를 아래 형식으로 추가하고 조회하는 API
    public List<ProductDTO> getProductsByStockValue() {
        return productRepository.findAll().stream().map(Product::toDTO).toList();
    }
}
