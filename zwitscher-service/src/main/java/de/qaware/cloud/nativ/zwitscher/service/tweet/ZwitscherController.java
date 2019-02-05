package de.qaware.cloud.nativ.zwitscher.service.tweet;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class ZwitscherController {
    private ZwitscherClient zwitscherClient;

    @Autowired
    public ZwitscherController(ZwitscherClient zwitscherClient) {
        this.zwitscherClient = zwitscherClient;
    }

    public Mono<List<ZwitscherMessage>> handleZwitscher(String query, int pagesize) {
        log.debug("HandleZwitscher");
        return zwitscherClient.search(query, pagesize).collectList();
    }
}
