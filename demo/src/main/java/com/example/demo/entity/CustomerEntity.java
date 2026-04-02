package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Removed jsonIgnore tag
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<OrderEntity> orders = new ArrayList<>();

    @Column(unique = true)
    private String customerId;

    private String name;
    private Integer age;
    private String email;
    private String phone;
    private String address;
    private String status;

    @Column(name ="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    private void generateCustomerId() {
        if (customerId == null) {
            customerId = "CUST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }

}
