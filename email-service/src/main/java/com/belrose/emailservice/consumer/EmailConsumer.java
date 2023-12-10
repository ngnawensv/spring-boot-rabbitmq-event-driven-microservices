package com.belrose.emailservice.consumer;

import com.belrose.emailservice.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailConsumer {

    @RabbitListener(queues = "${spring.queue.email.name}")
    public  void consumer(OrderEvent orderEvent){
        log.info(String.format("EmailConsumer->consumer(): Order event received => %s",orderEvent.toString()));

        //Save order event data in database
    }
}
