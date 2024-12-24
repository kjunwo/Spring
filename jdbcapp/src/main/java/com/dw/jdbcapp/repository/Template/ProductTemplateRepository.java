package com.dw.jdbcapp.repository.Template;

import com.dw.jdbcapp.exception.ResourceNotFoundException;
import com.dw.jdbcapp.model.Product;
import com.dw.jdbcapp.repository.iface.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductTemplateRepository implements ProductRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final RowMapper<Product> productRowMapper = new RowMapper<Product>() {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setProductId(rs.getInt("제품번호"));
            product.setProductName(rs.getString("제품명"));
            product.setPackageUnit(rs.getString("포장단위"));
            product.setUnitPrice(rs.getInt("단가"));
            product.setStock(rs.getInt("재고"));
            return product;
        }
    };
    @Override
    public List<Product> getAllProducts() {
        String query = "select * from 제품";
        return jdbcTemplate.query(query, productRowMapper);
    }

    @Override
    public Product getProductById(int number) {
        String query = "select * from 제품 where 제품번호 = ?";
        try {
            return jdbcTemplate.queryForObject(query, productRowMapper, number);
        }catch (EmptyResultDataAccessException e) {
            // 자바에 정의된 예외를 사용자정의예외로 바꿈으로 인해
            // CustomExceptionHandler의 코드를 단순하게 유지 가능
            // (예외들을 비슷한 유형으로 그룹지을 수 있음)
            throw new ResourceNotFoundException("제품번호가 올바르지 않습니다: " + number);
        }
    }

    @Override
    public Product saveProduct(Product product) {
        String query = "insert into 제품(제품번호, 제품명, 포장단위, 단가, 재고)" +
                "values(?,?,?,?,?)";
        jdbcTemplate.update(query,
                product.getProductId(),
                product.getProductName(),
                product.getPackageUnit(),
                product.getUnitPrice(),
                product.getStock());
        return product;
    }


    @Override
    public Product updateProduct(Product product) {
        String query = "update 제품 set 제품명 = ?, 포장단위 = ?, 단가 = ?, " + "재고 = ? where 제품번호 = ?";
        jdbcTemplate.update(query,
                product.getProductName(),
                product.getPackageUnit(),
                product.getUnitPrice(),
                product.getStock(),
                product.getProductId());
        return product;
    }

    @Override
    public int deleteProduct(int id) {
        String query = "delete from 제품 where 제품번호=?";
        jdbcTemplate.update(query,id);
        return id;
    }

    @Override
    public String updateProductWithStock(int id, int stock) {
        String query = "update 제품 set 재고 = ? where 제품번호 = ?";
        jdbcTemplate.update(query,stock,id);
        return "제품번호 : " + id + "의 재고가 " + stock + "개로 수정되었습니다.";
    }
}
