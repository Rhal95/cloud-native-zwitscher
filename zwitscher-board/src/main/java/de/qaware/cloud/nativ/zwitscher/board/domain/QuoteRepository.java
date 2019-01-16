/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 QAware GmbH, Munich, Germany
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package de.qaware.cloud.nativ.zwitscher.board.domain;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.util.concurrent.ListenableFutureCallback;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
public class QuoteRepository {

    private AsyncRabbitTemplate quoteTemplate;

    @Autowired
    private Exchange exchange;

    @Autowired
    public QuoteRepository(AsyncRabbitTemplate quoteTemplate) {
        this.quoteTemplate = quoteTemplate;
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public Mono<Quote> getNextQuote() {

        AsyncRabbitTemplate.RabbitConverterFuture<Quote> quote = quoteTemplate
                .convertSendAndReceiveAsType(exchange.getName(), "request", "quote", new ParameterizedTypeReference<Quote>() {
                });
        log.info("send request for quote");
        quote.addCallback(new ListenableFutureCallback<Quote>() {
            @Override
            public void onFailure(@NonNull Throwable ex) {
                log.error("resp failed", ex);
            }

            @Override
            public void onSuccess(@NonNull Quote result) {
                log.info("resp received: " + result.toString());
            }
        });
        return Mono.fromFuture(quote
                .completable())
                .doOnError((e) -> log.error("error occured with reply for queue", e));
    }

    protected Mono<Quote> fallback() {
        log.warn("using fallback quote");
        return Mono.just(new Quote("quote", "author"));
    }
}
