package de.qaware.cloud.nativ.zwitscher.board.domain;

import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@Profile("web")
public class ZwitscherWebClient implements ZwitscherClient{

    private WebClient client;

    public ZwitscherWebClient() {
        this.client = WebClient.create("http://localhost:8080");
    }

    @Override
    public Flux<Zwitscher> findByQ(String q) {
        return client.get()
                .uri("/search?query={q}", q)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Zwitscher.class);
    }
}
