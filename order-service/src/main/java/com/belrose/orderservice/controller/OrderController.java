package com.belrose.orderservice.controller;

import com.belrose.orderservice.dto.Order;
import com.belrose.orderservice.dto.OrderEvent;
import com.belrose.orderservice.publisher.OrderProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private  final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    public ResponseEntity<String> placerOrder(@RequestBody Order order){
        order.setOrderId(UUID.randomUUID().toString());
       var orderEvent= OrderEvent.builder()
                .status("PENDING")
                .message("Order is in pending status")
                .order(order)
                .build();

        orderProducer.sendMessage(orderEvent);

        return ResponseEntity.ok("Order sent to the RabbitMQ");
    }
}
