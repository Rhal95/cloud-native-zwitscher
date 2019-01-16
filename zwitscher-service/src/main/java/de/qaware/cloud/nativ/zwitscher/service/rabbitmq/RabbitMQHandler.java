package de.qaware.cloud.nativ.zwitscher.service.rabbitmq;

import de.qaware.cloud.nativ.zwitscher.service.quote.QuotesOnDesignClient;
import de.qaware.cloud.nativ.zwitscher.service.quote.RandomQuote;
import de.qaware.cloud.nativ.zwitscher.service.tweet.ZwitscherMessage;
import de.qaware.cloud.nativ.zwitscher.service.tweet.ZwitscherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RabbitListener(queues = "#{requestQueue.name}")
@Slf4j
@Component
public class RabbitMQHandler {
    private QuotesOnDesignClient quotesClient;
    private ZwitscherRepository zwitscherRepository;

    private final RabbitTemplate template;

    @Autowired
    public RabbitMQHandler(QuotesOnDesignClient quotesClient, ZwitscherRepository zwitscherRepository, RabbitTemplate template) {
        this.quotesClient = quotesClient;
        this.zwitscherRepository = zwitscherRepository;
        this.template = template;
    }

    //incoming parameters have to be unique. use wrapper classes (e.g. SearchParameter) or different queues

    @RabbitHandler
    public Mono<RandomQuote> handleQuote(String msg) {
        return quotesClient.getRandomQuote();
    }

    @RabbitHandler
    public Flux<ZwitscherMessage> handleZwitscher(SearchParameter parameter) {
        return zwitscherRepository.search(parameter.value, parameter.pageSize);
    }

}
