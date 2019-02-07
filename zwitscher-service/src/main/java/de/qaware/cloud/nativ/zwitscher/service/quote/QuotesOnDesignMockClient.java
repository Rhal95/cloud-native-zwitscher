package de.qaware.cloud.nativ.zwitscher.service.quote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
@Profile("test")
public class QuotesOnDesignMockClient implements QuotesOnDesignClient {
    private int minDelay = 20;
    private int maxDelay = 500;

    private Random random = new Random();

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
