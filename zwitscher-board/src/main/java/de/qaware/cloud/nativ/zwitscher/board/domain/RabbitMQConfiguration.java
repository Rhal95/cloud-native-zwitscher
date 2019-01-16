package de.qaware.cloud.nativ.zwitscher.board.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMQConfiguration {
    //anonymous queue
    private Queue replyQueue = QueueBuilder.nonDurable().build();

    @Autowired
    ConnectionFactory connectionFactory;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("app.zwitscher");
    }

    @Bean
    public Binding replyBinding(DirectExchange exchange) {
        return BindingBuilder.bind(replyQueue).to(exchange).with("reply");
    }

    @Bean
    public Queue replyQueue() {
        return replyQueue;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate template, Jackson2JsonMessageConverter messageConverter) {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        listenerContainer.setQueueNames(replyQueue.getName());
        template.setMessageConverter(messageConverter);
        return new AsyncRabbitTemplate(template, listenerContainer);
    }


}
