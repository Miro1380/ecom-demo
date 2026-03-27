package com.example.demo.service;

import com.example.demo.DTO.CreateCustomerRequest;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public CustomerEntity createCustomer(CreateCustomerRequest request){
        CustomerEntity customer = CustomerEntity.builder()
                .name(request.name())
                .age(request.age())
                .email(request.email())
                .phone(request.phone())
                .address(request.address())
                .status("ACTIVE")
                .build();

        CustomerEntity saved = customerRepository.save(customer);
        log.info("Created Customer account for {}", saved.getCustomerId());

        //TODO
        //Create message for Kafka producer here.

        return saved;
    }

    //Get all customers

    @Transactional(readOnly = true)
    public List<CustomerEntity> findAllCustomers(){
        return customerRepository.findAll();
    }

    //Find customer by customerId
    @Transactional(readOnly = true)
    public Optional<CustomerEntity> findCustomerById(String customerId){
        return customerRepository.findByCustomerId(customerId);
    }

    //Find all orders by customerId
    @Transactional(readOnly = true)
    public List<OrderEntity> findOrdersByCustomerId(String customerId){
       //Optional<CustomerEntity> customer = customerRepository.findByCustomerId(customerId);

        //Verify exists
       customerRepository.findByCustomerId(customerId)
               .orElseThrow(()-> new RuntimeException("Customer not found: "+ customerId));

       return orderRepository.findByCustomer_CustomerId(customerId);
    }

    //Find all customers by STATUS
    @Transactional(readOnly = true)
    public List<CustomerEntity> findCustomersByStatus(String status){
        return customerRepository.findByStatus(status);
    }
}
