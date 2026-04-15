package com.example.demo.controller;


import com.example.demo.DTO.CreateProductRequest;
import com.example.demo.entity.ProductEntity;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    //Inject services
    private final ProductService productService;

    //GET

    //Get all products
    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAllProducts(){
        //Use an empty check
        List<ProductEntity> productList = productService.getAllProducts();
        //log.info("Returning all products listed");
        return productList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(productList);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductEntity> getProductByProductId(@PathVariable String productId){
        ProductEntity item = productService.getProductByProductId(productId);
        return ResponseEntity.ok(item);
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<ProductEntity>> getProductByCategory(@PathVariable String category){
        List<ProductEntity> productList = productService.getProductByCategory(category);
        return productList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(productList);
    }

    @GetMapping("status/{status}")
    public ResponseEntity<List<ProductEntity>> getProductByStatus(@PathVariable String status){
        List<ProductEntity> productList = productService.getProductByStatus(status);
        return productList.isEmpty()? ResponseEntity.noContent().build()
                : ResponseEntity.ok(productList);
    }


    //Create Product
    @PostMapping
    public ResponseEntity<ProductEntity> onCreateProduct(@RequestBody CreateProductRequest request){
        ProductEntity saved = productService.createProduct(request);
        return ResponseEntity.ok(saved);
    }
}
