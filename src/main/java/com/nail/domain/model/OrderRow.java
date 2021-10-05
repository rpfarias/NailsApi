package com.nail.domain.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder // pode ser usada para gerar automaticamente um construtor para nossa classe.
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tbl_order_row")
public class OrderRow implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL) // muitas filas de pedidos para um produto
    @JoinColumn(name = "product_id", referencedColumnName = "id", updatable = false, insertable = false)
    private Product product;

    @Column(name = "product_id")
    private Long productId;

    private Integer quantity;

    private String productName;

    @Column(precision = 10, scale = 2)
    private BigDecimal productPrice;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", updatable = false, insertable = false)
    private Order order;

    @Column(name = "order_id")
    private Long orderId;
}
