package de.qaware.cloud.nativ.zwitscher.board.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMQConfiguration {

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate template, MessageConverter messageConverter) {
        template.setMessageConverter(messageConverter);
        return new AsyncRabbitTemplate(template);
    }


}
