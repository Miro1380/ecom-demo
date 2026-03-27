package com.example.demo.repository;

import com.example.demo.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    Optional<ProductEntity> findByProductId(String productId);
    List<ProductEntity> findByCategory(String category);
    List<ProductEntity> findByStatus(String status);
}
