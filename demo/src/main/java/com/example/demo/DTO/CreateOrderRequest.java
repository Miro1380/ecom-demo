package com.example.demo.DTO;

import java.math.BigDecimal;

public record CreateOrderRequest(
        String orderId,
        String productId,
        String customerId,
        int quantity,
        BigDecimal price,
        String status
){
    public CreateOrderRequest{
        if(status == null) status = "PENDING";
    }
}
