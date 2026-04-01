package com.example.demo.DTO;

public record CustomerStatusResponse(
   String customerId,
   String customerName,
   String status
) {}
