package com.nail.domain.request;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductRequest {

    @NotNull
    private String name;
    private String description;

    @Min(value = 1)
    private Integer quantity;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;
}
