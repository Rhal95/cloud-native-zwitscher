package de.qaware.cloud.nativ.zwitscher.service.quote;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class QuotesOnDesignMockClient implements QuotesOnDesignClient {
    int minDelay = 20;
    int maxDelay = 500;

    Random random = new Random();

    @Override
    public RandomQuote getRandomQuote() {
        try {
            Thread.sleep(minDelay + random.nextInt(maxDelay - minDelay));
        } catch (Exception e) {
            log.error("Error occured in the Sleeping Thread", e);
        }
        return new RandomQuote("Eine hässliche Lösung die funktioniert ist besser als keine Lösung.", "Unknown");
    }
}
