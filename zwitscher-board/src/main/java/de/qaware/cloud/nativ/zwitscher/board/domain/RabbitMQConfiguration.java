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
    private Queue replyQueue = QueueBuilder.durable("app.zwitscher.reply").build();
    private Queue requestQueue = QueueBuilder.durable("app.zwitscher.request").build();

    @Autowired
    ConnectionFactory connectionFactory;


    @Bean
    public Queue getReplyQueue() {
        return replyQueue;
    }

    @Bean
    public Queue getRequestQueue() {
        return requestQueue;
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("app.zwitscher");
    }


    @Bean
    Binding binding(DirectExchange exchange) {
        return BindingBuilder.bind(requestQueue)
                .to(exchange)
                .with("zwitscher");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate template) {
        log.info("port is: " + connectionFactory.getPort());
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        listenerContainer.setQueueNames(replyQueue.getName());
        return new AsyncRabbitTemplate(template, listenerContainer);
    }



}
