package com.example.demo.DTO;

import java.time.LocalDateTime;

public record UpdateOrderStatus(
        String orderId,
        String previousStatus,
        String newStatus,
        LocalDateTime updatedAt
) {}
