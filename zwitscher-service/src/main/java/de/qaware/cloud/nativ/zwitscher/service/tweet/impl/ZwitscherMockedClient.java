package de.qaware.cloud.nativ.zwitscher.service.tweet.impl;

import de.qaware.cloud.nativ.zwitscher.service.tweet.ZwitscherClient;
import de.qaware.cloud.nativ.zwitscher.service.tweet.ZwitscherMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Stream;

public class ZwitscherMockedClient implements ZwitscherClient {
    private Duration duration = Duration.ofMillis(100);

    @Override
    public Flux<ZwitscherMessage> search(String q, int pageSize) {
        return Mono.delay(duration).thenMany(Flux.fromStream(Stream.iterate(1, i->i+1)
                        .limit(pageSize)
                        .map(i->new ZwitscherMessage("Searched for: " + q + " nr " + i))));
    }
}
