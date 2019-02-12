package de.qaware.cloud.nativ.zwitscher.board.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Profile("web")
public class QuoteWebClient implements QuoteClient{

    private final WebClient client;

    @Autowired
    public QuoteWebClient() {
        this.client = WebClient.create("http://localhost:8080");
    }

    @Override
    public Mono<Quote> getNextQuote() {
        return client.get()
                .uri("/quote")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Quote.class);
    }
}
