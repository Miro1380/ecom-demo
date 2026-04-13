package com.example.demo.kafka;

import com.example.demo.DTO.CreateOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
//@RequiredArgsConstructor
public class OrderProducer {
    private final KafkaTemplate<String, CreateOrderRequest> kafkaTemplate;
    //private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OrderProducer.class);

    //Initialize template
    public OrderProducer(KafkaTemplate<String, CreateOrderRequest> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(CreateOrderRequest request){
        log.info("Producing message to Kafka: {}", request);

        try {
            kafkaTemplate.send("order", request.orderId(), request)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.warn("Kafka send failed, skipping: {}", ex.getMessage());
                        }
                    });
        } catch (Exception e) {
            log.warn("Kafka unavailable, skipping message: {}", e.getMessage());
        }
    }
}
