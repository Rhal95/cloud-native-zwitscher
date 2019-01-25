package de.qaware.cloud.nativ.zwitscher.service.quote.impl;

import de.qaware.cloud.nativ.zwitscher.service.quote.QuotesOnDesignClient;
import de.qaware.cloud.nativ.zwitscher.service.quote.RandomQuote;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

//reactive client to access the Quotes on Design service
public class QuotesOnDesignReactiveClient implements QuotesOnDesignClient {

    private WebClient quotesOnDesign = WebClient.create("http://quotesondesign.com");

    public Mono<RandomQuote> getRandomQuote() {
        return quotesOnDesign
                .get()
                .uri("/api/3.0/api-3.0.json")
                .accept(MediaType.parseMediaType("text/x-json"))
                .exchange()
                .flatMap(response -> response.body((inputMessage, context) ->
                        context.messageReaders()
                                .stream()
                                .filter(f -> f.canRead(ResolvableType.forClass(RandomQuote.class), MediaType.APPLICATION_JSON)) //find a Reader that understands json and can decode a RandomQuote
                                .findFirst().orElseThrow(() -> new MessageConversionException("No message reader found for reading RandomQuote as JSON."))
                                .readMono(ResolvableType.forClass(RandomQuote.class), inputMessage, new HashMap<>())
                                .map(e -> (RandomQuote) e) // we just read a RandomQuote so the type is actually fixed here.
                ));
    }
}