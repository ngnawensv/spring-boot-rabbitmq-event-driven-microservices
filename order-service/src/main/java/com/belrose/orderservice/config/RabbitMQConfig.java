package com.belrose.orderservice.config;

import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.exchange.name}")
    private  String exchange;


    @Value("${spring.rabbitmq.queue.order.name}")
    private  String orderQueue;

    @Value("${spring.rabbitmq.queue.email.name}")
    private  String emailQueue;


    @Value("${spring.rabbitmq.routing.order.key}")
    private  String orderRoutingKey;
    @Value("${spring.rabbitmq.routing.email.key}")
    private  String emailRoutingKey;

    //spring bean for rabbitmq queue
    @Bean
    public Queue orderQueue() {
        return new Queue(this.getOrderQueue());
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(this.getEmailQueue());
    }

    //spring bean for rabbitmq exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(this.getExchange());
    }


    //Binding between queue and exchange using routing key
    @Bean
    public Binding orderBinding(){
        return BindingBuilder.bind(orderQueue())
                .to(exchange())
                .with(this.getOrderRoutingKey());
    }
    @Bean
    public Binding emailBinding(){
        return BindingBuilder.bind(emailQueue())
                .to(exchange())
                .with(this.getEmailRoutingKey());
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
    // those 3 beans are autoconfigure, not explecite configuration require
    //ConnectionFactory
    //RabbitTemplate
    //RabbitAdmin
}
