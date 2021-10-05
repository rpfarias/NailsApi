package com.nail.domain.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class OrderRequest {

    @NotEmpty(message = "The product list cannot be empty")
    private Set<ProductOrderRequest> products;
}
