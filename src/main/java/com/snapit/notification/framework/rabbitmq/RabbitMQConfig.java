package com.snapit.notification.framework.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange framesExtractionFailedExchange() {
        return new TopicExchange("frames-extraction-failed-exchange");
    }

    @Bean
    public Queue framesExtractionFailedQueue() {
        return new Queue("frames-extraction-failed-notification-queue", true);
    }

    @Bean
    public Binding framesExtractionFailedBinding(Queue framesExtractionFailedQueue, TopicExchange framesExtractionFailedExchange) {
        return BindingBuilder.bind(framesExtractionFailedQueue).to(framesExtractionFailedExchange).with("frames");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}