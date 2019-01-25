package de.qaware.cloud.nativ.zwitscher.service.config;

import de.qaware.cloud.nativ.zwitscher.service.quote.QuotesOnDesignClient;
import de.qaware.cloud.nativ.zwitscher.service.quote.impl.QuotesOnDesignMockedClient;
import de.qaware.cloud.nativ.zwitscher.service.quote.impl.QuotesOnDesignReactiveClient;
import de.qaware.cloud.nativ.zwitscher.service.quote.impl.RandomQuoteFallback;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//Configuration to select the right Client
@Configuration
public class QuotesOnDesignConfiguration {

    @Bean
    @Profile("native")
    QuotesOnDesignClient reactiveClient() {
        return new QuotesOnDesignReactiveClient();
    }

    @Bean
    @Profile("test")
    QuotesOnDesignClient mockedClient() {
        return new QuotesOnDesignMockedClient();
    }

    @Bean
    @ConditionalOnMissingBean
    QuotesOnDesignClient fallbackClient() {
        return new RandomQuoteFallback();
    }
}