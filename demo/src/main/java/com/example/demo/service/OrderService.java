package com.example.demo.service;

import com.example.demo.DTO.CreateOrderRequest;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.kafka.OrderProducer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.repository.OrderRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;


    public void placeOrder(CreateOrderRequest createOrderRequest){
        CustomerEntity customer =  customerRepository.findByCustomerId(createOrderRequest.customerId())
                .orElseThrow( ()-> new RuntimeException("Customer not found: "+ createOrderRequest.customerId()));

        ProductEntity product = productRepository.findByProductId(createOrderRequest.productId())
                .orElseThrow(()-> new RuntimeException("Product not found: "+ createOrderRequest.productId()));

        OrderEntity entity = OrderEntity.onOrderPlaced(createOrderRequest,product,customer);

        orderRepository.save(entity);
        log.info("Order saved to database: {}", entity.getOrderId());

        orderProducer.sendMessage(createOrderRequest);
        log.info("Order flow complete for: {}", createOrderRequest.orderId());
    }

    @Transactional
    public void updateOrderStatus(String orderId, String status){
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not Found"+ orderId));

        //Update Entity and save to db
        orderEntity.setStatus(status);
        orderRepository.save(orderEntity);
        log.info("Order status updated in DB: {} -> {}", orderId, status);

        //Make new order event, updateEvent
        CreateOrderRequest updateEvent = new CreateOrderRequest(
                orderEntity.getOrderId(),
                orderEntity.getProduct().getProductId(),
                orderEntity.getCustomer().getCustomerId(),
                orderEntity.getQuantity(),
                orderEntity.getPrice(),
                status
        );

        orderProducer.sendMessage(updateEvent);
        log.info("Order status event sent to Kafka: {} -> {}", orderId,status);
    }


    @Transactional(readOnly = true)
    public List<OrderEntity> findOrdersByStatus(String status){
        return orderRepository.findOrdersByStatus(status);
    }

    @Transactional(readOnly = true)
    public Optional<OrderEntity> findByOrderId(String orderId){
        return orderRepository.findByOrderId(orderId);
    }

    @Transactional(readOnly = true)
    public List<OrderEntity> findAllOrders(){
        return orderRepository.findAll();
    }
}
