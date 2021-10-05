package com.nail.mapper;

import com.nail.domain.model.Order;
import com.nail.domain.model.OrderRow;
import com.nail.domain.response.OrderResponse;
import com.nail.domain.response.ProductOrderResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .purchaseDate(order.getPurchaseDate())
                .total(order.getTotal())
                .clientId(order.getClientId())
                .rows(toProductOrderResponseList(order.getRows()))
                .build();
    }

    public static List<OrderResponse> toResponseList(List<Order> orders) {
        List<OrderResponse> response = new ArrayList<>();
        for (Order order : orders) {
            response.add(toResponse(order));
        }
        return response;
    }

    public static ProductOrderResponse toProductOrderResponse(OrderRow row) {
        return ProductOrderResponse.builder()
                .rowId(row.getId())
                .productId(row.getProductId())
                .name(row.getProductName())
                .quantity(row.getQuantity())
                .price(row.getProductPrice())
                .total(row.getProductPrice().multiply(BigDecimal.valueOf(row.getQuantity())))
                .build();
    }

    public static List<ProductOrderResponse> toProductOrderResponseList(List<OrderRow> rows) {
        List<ProductOrderResponse> response = new ArrayList<>();
        for (OrderRow row : rows) {
            response.add(toProductOrderResponse(row));
        }
        return response;
    }
}
