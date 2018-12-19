package de.qaware.cloud.nativ.zwitscher.service.quote;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import de.qaware.cloud.nativ.zwitscher.service.ZwitscherServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZwitscherServiceApplication.class)
@Import(QuotesOnDesignReactiveClient.class)
@ActiveProfiles({"test", "native"})
public class QuotesOnDesignReactiveClientTest {

    WireMockRule wiremock = new WireMockRule();

    @Autowired
    QuotesOnDesignReactiveClient client;

    public QuotesOnDesignReactiveClientTest() {
        wiremock.start();
    }

    @Test
    public void decodeMessage() {
        stubFor(get("http://quotesondesign.com/api/3.0/api-3.0.json")
                .willReturn(aResponse().withBodyFile("randomQuote.json"))
                .withHeader("MediaType", equalTo("text/x-json")));

        Mono<RandomQuote> result = client.getRandomQuote();
        assertThat(result.block()).isNotNull();

    }
}