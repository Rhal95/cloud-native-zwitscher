package de.qaware.cloud.nativ.zwitscher.service;

import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.social.twitter.api.Twitter;

@Configuration
public class MockTwitterConfiguration {
    @ConditionalOnMissingBean
    @Primary
    @Bean
    public Twitter twitter() {
        return Mockito.mock(Twitter.class);
    }
}