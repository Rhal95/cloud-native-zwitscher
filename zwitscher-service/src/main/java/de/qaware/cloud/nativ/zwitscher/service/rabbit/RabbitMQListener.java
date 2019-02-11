package de.qaware.cloud.nativ.zwitscher.service.rabbit;

import de.qaware.cloud.nativ.zwitscher.common.transfer.QuoteRequest;
import de.qaware.cloud.nativ.zwitscher.common.transfer.ZwitscherRequest;
import de.qaware.cloud.nativ.zwitscher.service.quote.QuotesOnDesignController;
import de.qaware.cloud.nativ.zwitscher.service.quote.RandomQuote;
import de.qaware.cloud.nativ.zwitscher.service.tweet.ZwitscherController;
import de.qaware.cloud.nativ.zwitscher.service.tweet.ZwitscherMessage;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@RabbitListener(queues = "#{requestQueue.name}", containerFactory = "MyRabbitListenerContainerFactory")
@Component
public class RabbitMQListener {

    @Autowired
    public RabbitMQListener(ZwitscherController zwitscherController, QuotesOnDesignController quotesOnDesignController) {
        this.zwitscherController = zwitscherController;
        this.quotesOnDesignController = quotesOnDesignController;
    }

    private ZwitscherController zwitscherController;
    private QuotesOnDesignController quotesOnDesignController;

    @RabbitHandler
    public Mono<List<ZwitscherMessage>> handleZwitscher(ZwitscherRequest request) {
        return zwitscherController.handleZwitscher(request.getQuery(), request.getNumber());
    }

    @RabbitHandler
    public Mono<RandomQuote> handleQuote(QuoteRequest request) {
        return quotesOnDesignController.getQuote();
    }

}
