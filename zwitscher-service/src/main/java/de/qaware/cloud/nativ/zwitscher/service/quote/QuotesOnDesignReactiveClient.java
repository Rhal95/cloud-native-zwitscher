package de.qaware.cloud.nativ.zwitscher.service.quote;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//reactive client to access the Quotes on Design service
@Component(value = "quotesOnDesign")
public class QuotesOnDesignReactiveClient implements QuotesOnDesignClient {

    WebClient quotesOnDesign;

    public QuotesOnDesignReactiveClient() {
        this.quotesOnDesign = WebClient.create("http://quotesondesign.com");
    }


    public Mono<RandomQuote> getRandomQuote() {
        return quotesOnDesign
                .get()
                .uri("/api/3.0/api-3.0.json")
                .retrieve().bodyToMono(RandomQuote.class);

    }
}
