package de.qaware.cloud.nativ.zwitscher.service.quote;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableWebFlux
@Configuration
public class QuotesOnDesignReactiveConfiguration implements WebFluxConfigurer {

    @Autowired
    ObjectMapper mapper;

}
