package com.nail.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderResponse {

    private Long rowId;
    private Long productId;
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal total;
}
