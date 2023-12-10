package com.belrose.orderservice.publisher;

import com.belrose.orderservice.config.RabbitMQConfig;
import com.belrose.orderservice.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProducer {
    private final RabbitMQConfig rabbitMQConfig;
    private final RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitMQConfig rabbitMQConfig, RabbitTemplate rabbitTemplate) {
        this.rabbitMQConfig = rabbitMQConfig;
        this.rabbitTemplate = rabbitTemplate;
    }

    public  void sendMessage(OrderEvent orderEvent){
        log.info(String.format("OrderProducer->sendMessage(): OrderEvent event sent to RabbitMQ => %s",orderEvent));
        //Sent order event to order queue
        rabbitTemplate.convertAndSend(rabbitMQConfig.getExchange(),rabbitMQConfig.getOrderRoutingKey(),orderEvent);
        //Sent order event to email queue
        rabbitTemplate.convertAndSend(rabbitMQConfig.getExchange(),rabbitMQConfig.getEmailRoutingKey(),orderEvent);
    }
}
