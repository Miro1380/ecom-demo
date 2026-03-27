package com.example.demo.service;

import com.example.demo.DTO.CreateProductRequest;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    //Inject Repo
    private final ProductRepository productRepository;

    public ProductEntity createProduct(CreateProductRequest request){
        ProductEntity product = ProductEntity.builder()
                .productId(request.productId())
                .name(request.name())
                .description(request.description())
                .category(request.category())
                .stockQuantity(request.stockQuantity())
                .price(request.price())
                .status("AVAILABLE")
                .build();

        //Save to repo,db
        ProductEntity saved = productRepository.save(product);
        log.info("Created product with info: {},{}", saved.getProductId(),saved.getName());

        //TODO ADD KAFKA message??
        return saved;
    }

    //GETs
    //Get All products
    @Transactional(readOnly = true)
    public List<ProductEntity> getAllProducts(){
        return productRepository.findAll();
    }

    //Get by productId
    @Transactional(readOnly = true)
    public ProductEntity getProductByProductId(String productId){
        return productRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("No such product Id found: "+ productId));
    }

    //TODO Get products by category
    @Transactional(readOnly = true)
    public List<ProductEntity> getProductByCategory(String category){
        return productRepository.findByCategory(category);
    }
    //TODO Get product by status
    @Transactional(readOnly = true)
    public List<ProductEntity> getProductByStatus(String status){
        return productRepository.findByStatus(status);
    }


}
