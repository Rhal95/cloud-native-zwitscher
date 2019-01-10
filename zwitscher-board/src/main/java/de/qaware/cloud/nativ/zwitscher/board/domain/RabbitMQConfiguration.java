package de.qaware.cloud.nativ.zwitscher.board.domain;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    private Queue twitterRecv = new Queue("twitterRecv");
    private Queue twitterSend = new Queue("twitterSend");

    private Queue quoteRecv = new Queue("quoteRecv");
    private Queue quoteSend = new Queue("quoteSend");

    @Bean
    public AsyncRabbitTemplate twitterTemplate(RabbitTemplate template) {
        template.setRoutingKey(twitterSend.getName());
        template.setReplyAddress(twitterRecv.getName());
        AsyncRabbitTemplate asyncRabbitTemplate = new AsyncRabbitTemplate(template);
        asyncRabbitTemplate.start();
        return asyncRabbitTemplate;
    }

    @Bean
    public AsyncRabbitTemplate quoteTemplate(RabbitTemplate template) {
        template.setRoutingKey(quoteSend.getName());
        template.setReplyAddress(quoteRecv.getName());
        AsyncRabbitTemplate asyncRabbitTemplate = new AsyncRabbitTemplate(template);
        asyncRabbitTemplate.start();
        return asyncRabbitTemplate;
    }

}
