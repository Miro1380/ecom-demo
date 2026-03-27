package com.example.demo.DTO;

public record CreateCustomerRequest(
        String name,
        Integer age,
        String email,
        String phone,
        String address
)
{}
