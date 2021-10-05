package com.nail.domain.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ProductOrderRequest {

    @NotNull
    private Long productId;

    @Min(value = 1)
    private Integer quantity;
}
