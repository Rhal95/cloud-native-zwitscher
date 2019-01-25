package de.qaware.cloud.nativ.zwitscher.service.tweet;

import de.qaware.cloud.nativ.zwitscher.common.transfer.ZwitscherRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RabbitListener(queues = "#{requestQueue.name}")
public class ZwitscherListener {

    private ZwitscherClient zwitscherClient;

    public ZwitscherListener(ZwitscherClient zwitscherClient) {
        this.zwitscherClient = zwitscherClient;
    }

    @RabbitHandler
    public Mono<List<ZwitscherMessage>> handleZwitscher(ZwitscherRequest request) {
        log.debug("HandleZwitscher");
        return zwitscherClient.search(request.getQuery(), request.getNumber()).collectList();
    }
}
