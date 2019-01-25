package de.qaware.cloud.nativ.zwitscher.service.tweet.impl;

import de.qaware.cloud.nativ.zwitscher.service.tweet.ZwitscherClient;
import de.qaware.cloud.nativ.zwitscher.service.tweet.ZwitscherMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

@Component
@Profile("test")
public class ZwitscherMockedClient implements ZwitscherClient {
    private int minDelay = 1; //ms
    private int maxDelay = 5; //ms

    private Random random = new Random();

    private Duration getRandomDuration() {
        return Duration.ofMillis(minDelay + random.nextInt(maxDelay - minDelay));
    }

    @Override
    public Flux<ZwitscherMessage> search(String q, int pageSize) {
        return Flux.range(1, pageSize)
                .flatMap(i -> Mono.just(new ZwitscherMessage("Searched for: " + q + "\tnr: " + i))).delayElements(getRandomDuration());
    }
}
