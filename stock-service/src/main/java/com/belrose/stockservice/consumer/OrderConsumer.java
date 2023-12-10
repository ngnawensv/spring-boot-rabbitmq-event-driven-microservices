package com.belrose.stockservice.consumer;

import com.belrose.stockservice.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderConsumer {

    @RabbitListener(queues = "${spring.queue.order.name}")
    public  void consumer(OrderEvent orderEvent){
        log.info(String.format("OrderConsumer->consumer(): Order event received => %s",orderEvent.toString()));

        //Save order event data in database
    }
}
