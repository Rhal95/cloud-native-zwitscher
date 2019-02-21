package de.qaware.cloud.nativ.zwitscher.service.quote.impl;

import de.qaware.cloud.nativ.zwitscher.service.quote.QuotesOnDesignClient;
import de.qaware.cloud.nativ.zwitscher.service.quote.RandomQuote;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

public class QuotesOnDesignMockedClient implements QuotesOnDesignClient {
    private int delay = 100; //ms

    private Random random = new Random();

    //delays the response by a random amount
    @Override
    public Mono<RandomQuote> getRandomQuote() {
        return Mono.delay(Duration.ofMillis(delay)).thenReturn(new RandomQuote("Kann der Zufall zufällig auch nicht Zufall sein?", "Merten M."));
    }
}
