package com.example.demo.DTO;

import java.math.BigDecimal;

public record OrderResponse(
        String orderId,
        Integer quantity,
        BigDecimal price,
        String status,
        String productName,
        String productId
) {}
