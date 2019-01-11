package de.qaware.cloud.nativ.zwitscher.service.quote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RabbitListener(queues = "app.zwitscher.request")
@Slf4j
@Component
public class RandomQuoteHandler {
    private RandomQuoteController controller;

    private final RabbitTemplate template;

    @Autowired
    public RandomQuoteHandler(RandomQuoteController controller, RabbitTemplate template) {
        this.controller = controller;
        this.template = template;
    }

    @RabbitHandler
    public void handleQuote(String msg) {
        log.debug("received msg '" + msg + "'");
        switch (msg) {
            case "getQuote":
            case "quote":
                controller.quote().subscribe((quote) -> {
                    template.convertAndSend("app.zwitscher.reply", quote);
                    log.debug(quote.toString());
                });

            default:
        }
    }
}
