package de.qaware.cloud.nativ.zwitscher.service.quote;

import reactor.core.publisher.Mono;

public interface QuotesOnDesignClient {
    Mono<RandomQuote> getRandomQuote();
}
