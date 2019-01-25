package de.qaware.cloud.nativ.zwitscher.service.quote;

import de.qaware.cloud.nativ.zwitscher.common.transfer.QuoteRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@Slf4j
@RabbitListener(queues = "#{requestQueue.name}")
public class RandomQuoteListener {

    private QuotesOnDesignClient quotesClient;

    @Autowired
    public RandomQuoteListener(QuotesOnDesignClient quotesClient) {
        this.quotesClient = quotesClient;
    }

    @RabbitHandler
    public Mono<RandomQuote> handleQuote(QuoteRequest request) {
        log.debug("HandleQuote");
        return quotesClient.getRandomQuote();
    }
}
