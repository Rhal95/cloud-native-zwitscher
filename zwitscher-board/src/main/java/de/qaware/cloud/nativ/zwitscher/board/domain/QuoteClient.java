package de.qaware.cloud.nativ.zwitscher.board.domain;

import reactor.core.publisher.Mono;

public interface QuoteClient {
    Mono<Quote> getNextQuote();
}
