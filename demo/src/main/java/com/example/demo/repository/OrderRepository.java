package com.example.demo.repository;

import com.example.demo.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository  extends JpaRepository<OrderEntity,Long> {

    List<OrderEntity> findOrdersByStatus(String status);
    Optional<OrderEntity> findByOrderId(String OrderId);
    List<OrderEntity> findByCustomer_CustomerId(String customerId);


}
