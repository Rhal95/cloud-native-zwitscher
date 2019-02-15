package de.qaware.cloud.nativ.zwitscher.service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableRabbit
@Profile("rabbit")
public class RabbitMQConfiguration {

    Queue quoteQueue = QueueBuilder.nonDurable().autoDelete().build();
    Queue zwitscherQueue = QueueBuilder.nonDurable().autoDelete().build();

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue quoteQueue() {
        return quoteQueue;
    }

    @Bean
    Queue zwitscherQueue() {
        return zwitscherQueue;
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("app.zwitscher");
    }

    @Bean
    public Binding quoteBinding(DirectExchange exchange) {
        return BindingBuilder.bind(quoteQueue).to(exchange).with("quote");
    }

    @Bean
    public Binding zwitscherBinding(DirectExchange exchange) {
        return BindingBuilder.bind(zwitscherQueue).to(exchange).with("search");
    }

    @Bean
    public SimpleRabbitListenerContainerFactory MyRabbitListenerContainerFactory(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }



}
