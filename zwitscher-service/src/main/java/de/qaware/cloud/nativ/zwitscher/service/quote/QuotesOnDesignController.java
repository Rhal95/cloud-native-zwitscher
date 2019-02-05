package de.qaware.cloud.nativ.zwitscher.service.quote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class QuotesOnDesignController {
    private QuotesOnDesignClient quotesClient;

    @Autowired
    public QuotesOnDesignController(QuotesOnDesignClient quotesClient) {
        this.quotesClient = quotesClient;
    }

    public Mono<RandomQuote> getQuote() {
        log.debug("HandleQuote");
        return quotesClient.getRandomQuote();
    }
}
