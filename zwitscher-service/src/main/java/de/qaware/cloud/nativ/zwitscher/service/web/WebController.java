package de.qaware.cloud.nativ.zwitscher.service.web;

import de.qaware.cloud.nativ.zwitscher.service.quote.QuotesOnDesignController;
import de.qaware.cloud.nativ.zwitscher.service.quote.RandomQuote;
import de.qaware.cloud.nativ.zwitscher.service.tweet.ZwitscherClient;
import de.qaware.cloud.nativ.zwitscher.service.tweet.ZwitscherMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@Profile("web")
public class WebController {

    private final ZwitscherClient zwitscherClient;
    private final QuotesOnDesignController quotesOnDesignController;

    @Autowired
    public WebController(ZwitscherClient zwitscherClient, QuotesOnDesignController quotesOnDesignController) {
        this.zwitscherClient = zwitscherClient;
        this.quotesOnDesignController = quotesOnDesignController;
    }

    @GetMapping(path = "/quote", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Mono<RandomQuote> getRandomQuote(){
        return quotesOnDesignController.getQuote();
    }

    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Flux<ZwitscherMessage> getZwitscherMessage(@RequestParam String query, @RequestParam(required = false, defaultValue = "50") int pageSize){
        return zwitscherClient.search(query, pageSize);
    }
}
