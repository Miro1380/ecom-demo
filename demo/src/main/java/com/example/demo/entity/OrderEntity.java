package com.example.demo.entity;

import com.example.demo.DTO.CreateOrderRequest;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {

    private static final String PENDING = "Pending";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id")
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private ProductEntity product;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;
    private int quantity;
    private BigDecimal price;
    private String status;

    private LocalDateTime createdAt;

    public static OrderEntity onOrderPlaced(CreateOrderRequest request, ProductEntity product, CustomerEntity customer){
        return OrderEntity.builder()
                .orderId(request.orderId())
                .product(product)
                .customer(customer)
                .quantity(request.quantity())
                .price(request.price())
                .status(PENDING)
                .build();
    }

    public static OrderEntity onOrderCanceled(){
        return OrderEntity.builder()
                .status("canceled")
        .build();
    }

    @PrePersist
    private void generateOrderId() {
        if (orderId == null) {
            orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }
}
