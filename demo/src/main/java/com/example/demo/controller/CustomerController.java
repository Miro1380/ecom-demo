package com.example.demo.controller;

import com.example.demo.DTO.CreateCustomerRequest;
import com.example.demo.DTO.CustomerStatusResponse;
import com.example.demo.DTO.OrderResponse;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/customers")

public class CustomerController {

    private final CustomerService customerService;

    //GETs

    //Get all customers
    @GetMapping
    public ResponseEntity<List<CustomerEntity>> getAllCustomers(){
        List<CustomerEntity> customerList = customerService.findAllCustomers();
        return customerList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(customerList);
    }

    //Get customer by customerId
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerEntity> getByCustomerId(@PathVariable String customerId){
        return customerService.findCustomerById(customerId)
                .map(ResponseEntity:: ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    //Get all orders for a customer
    @GetMapping("/{customerId}/orders")
    public ResponseEntity<List<OrderResponse>> getOrdersByCustomerId(@PathVariable String customerId){
        List<OrderResponse> ordersList = customerService.findOrdersByCustomerId(customerId);
        return ordersList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(ordersList);
    }

    //Get Status of all customers of type 'status' ("ACTIVE,INACTIVE",etc.)
    @GetMapping("/status/{status}")
    public ResponseEntity<List<CustomerStatusResponse>> getCustomersByStatus(@PathVariable String status){
        List<CustomerStatusResponse> customerList = customerService.findCustomersByStatus(status);
        return customerList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(customerList);
    }

    //POSTs

    //Should change to a customerDTO ?
    @PostMapping
    public ResponseEntity<CustomerEntity> onCreateCustomer(@RequestBody CreateCustomerRequest request){
        CustomerEntity saved = customerService.createCustomer(request);
        return ResponseEntity.ok(saved);
    }
}
