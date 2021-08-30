package com.nexti.domain.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity(name = "tbl_product")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer quantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @ToString.Exclude
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderRow> rows;
}
