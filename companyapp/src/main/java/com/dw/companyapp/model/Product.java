package com.dw.companyapp.model;

import com.dw.companyapp.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name="제품")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="제품번호")
    private Long productId;
    @Column(name="제품명")
    private String productName;
    @Column(name="포장단위")
    private String pkgUnit;
    @Column(name="단가")
    private double unitPrice;
    @Column(name="재고")
    private int stock;

    public ProductDTO toDTO() {
        return new ProductDTO(this.productId,
                this.productName,
                this.unitPrice,
                this.stock,
                this.unitPrice * this.stock);
    }
}
