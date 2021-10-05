package com.nail.domain.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal price;
}
