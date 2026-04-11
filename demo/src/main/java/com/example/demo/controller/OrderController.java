package com.example.demo.controller;

import com.example.demo.DTO.CreateOrderRequest;
import com.example.demo.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.OrderService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    //Inject service
    private final OrderService orderService;

    /* If lombok fails
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }*/

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest request){
        orderService.placeOrder(request);
        return ResponseEntity.ok("Order placed and event sent to kafka");
    }

    @GetMapping
    public ResponseEntity<List<OrderEntity>> getAllOrders(){
        List<OrderEntity> orders = orderService.findAllOrders();
        return orders.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderEntity> getByOrderId(@PathVariable String orderId){
        return orderService.findByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{orderStatus}")
    public ResponseEntity<List<OrderEntity>> getByOrderStatus(@PathVariable String orderStatus){
        List<OrderEntity> orders = orderService.findOrdersByStatus(orderStatus);
        return orders.isEmpty()?
                ResponseEntity.noContent().build():
                ResponseEntity.ok(orders);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable String orderId,@RequestParam String status){
        orderService.updateOrderStatus(orderId,status);
        return ResponseEntity.ok("Order Status updated for order:" + orderId);
    }
}
