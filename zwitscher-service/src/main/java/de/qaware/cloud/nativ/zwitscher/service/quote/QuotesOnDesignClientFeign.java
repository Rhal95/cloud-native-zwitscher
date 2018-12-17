package de.qaware.cloud.nativ.zwitscher.service.quote;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "quotesOnDesign", url = "http://quotesondesign.com",
        configuration = QuotesOnDesignFeignConfiguration.class,
        fallback = RandomQuoteFallback.class)
public interface QuotesOnDesignClientFeign extends QuotesOnDesignClient {
}
