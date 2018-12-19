package de.qaware.cloud.nativ.zwitscher.service.quote;

import de.qaware.cloud.nativ.zwitscher.service.ZwitscherServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZwitscherServiceApplication.class)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles({"test", "native"})
public class QuotesOnDesignReactiveClientTest {


    @Autowired
    QuotesOnDesignReactiveClient client;


    @Test
    public void decodeMessage() {
        stubFor(get("http://quotesondesign.com/api/3.0/api-3.0.json")
                .willReturn(aResponse().withBodyFile("randomQuote.json"))
                .withHeader("MediaType", equalTo("text/x-json")));

        Mono<RandomQuote> result = client.getRandomQuote();
        assertThat(result.block()).isNotNull();

    }
}