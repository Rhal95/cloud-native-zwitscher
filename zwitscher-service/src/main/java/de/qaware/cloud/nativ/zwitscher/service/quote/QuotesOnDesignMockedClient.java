package de.qaware.cloud.nativ.zwitscher.service.quote;

import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

public class QuotesOnDesignMockedClient implements QuotesOnDesignClient {
    private int minDelay = 20; //ms
    private int maxDelay = 500; //ms

    private Random random = new Random();

    //delays the response by a random amount
    @Override
    public Mono<RandomQuote> getRandomQuote() {
        return Mono.delay(getRandomDuration()).thenReturn(new RandomQuote("Kann der Zufall zufällig auch nicht Zufall sein?", "Merten M."));
    }

    private Duration getRandomDuration() {
        return Duration.ofMillis(minDelay + random.nextInt(maxDelay - minDelay));
    }
}
