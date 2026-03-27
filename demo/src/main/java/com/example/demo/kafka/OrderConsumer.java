package com.example.demo.kafka;

import com.example.demo.DTO.CreateOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderConsumer {

    @KafkaListener(topics="order", groupId="order-group")
    public void consume(CreateOrderRequest request){
        log.info("Consumer received order event: {}", request);

        //Add any logic here
        System.out.println("Processing order: "+ request.orderId());
        log.info("Processing order:{}",request.orderId());
    }
}
