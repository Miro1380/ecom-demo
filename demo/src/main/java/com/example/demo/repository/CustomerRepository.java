package com.example.demo.repository;

import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {

    Optional<CustomerEntity> findByCustomerId(String customerId);
    List<CustomerEntity>findByStatus(String status);
}
