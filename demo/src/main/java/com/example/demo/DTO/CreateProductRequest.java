package com.example.demo.DTO;

import jakarta.persistence.Column;

import java.math.BigDecimal;

public record CreateProductRequest(
         String productId,
         String name,
         String description,
         String category,
         Integer stockQuantity,
         BigDecimal price
) { }
