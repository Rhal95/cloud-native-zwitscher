package de.qaware.cloud.nativ.zwitscher.service.quote;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@ConditionalOnProperty(value = "feign.enabled", havingValue = "true")
@FeignClient(name = "quotesOnDesign", url = "http://quotesondesign.com",
        configuration = QuotesOnDesignConfiguration.class,
        fallback = RandomQuoteFallback.class)
@Profile("zwitscher")
public interface QuotesOnDesignClientFeign extends QuotesOnDesignClient {
    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/api/3.0/api-3.0.json")
    RandomQuote getRandomQuote();
}
