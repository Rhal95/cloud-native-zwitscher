package de.qaware.cloud.nativ.zwitscher.board.domain;

import reactor.core.publisher.Flux;

public interface ZwitscherClient {
    Flux<Zwitscher> findByQ(final String q);
}
