package de.qaware.cloud.nativ.zwitscher.service.tweet;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

@Repository
@Profile("test")
public class ZwitscherMockedRepository implements ZwitscherRepository {
    private int minDelay = 1; //ms
    private int maxDelay = 5; //ms

    private Random random = new Random();

    private Duration getRandomDuration() {
        return Duration.ofMillis(minDelay + random.nextInt(maxDelay - minDelay));
    }

    @Override
    public Flux<ZwitscherMessage> search(String q, int pageSize) {
        return Flux.range(1, pageSize + 1)
                .flatMap(i -> Mono.just(new ZwitscherMessage("msg with number " + i))).delayElements(getRandomDuration());
    }
}
