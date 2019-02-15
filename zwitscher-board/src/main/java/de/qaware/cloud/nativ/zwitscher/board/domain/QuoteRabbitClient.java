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
import de.qaware.cloud.nativ.zwitscher.common.transfer.QuoteRequest;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Profile("rabbit")
public class QuoteRabbitClient implements QuoteClient{

    private AsyncRabbitTemplate rabbitTemplate;

    @Autowired
    public QuoteRabbitClient(AsyncRabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @HystrixCommand(fallbackMethod = "fallback", ignoreExceptions = Exception.class)
    public Mono<Quote> getNextQuote() {
        AsyncRabbitTemplate.RabbitConverterFuture<Quote> request = rabbitTemplate
                .convertSendAndReceiveAsType("app.zwitscher", "quote", new QuoteRequest(), new ParameterizedTypeReference<Quote>() {
                });
        return Mono.fromFuture(request.completable());
    }

    protected Mono<Quote> fallback() {
        return Mono.just(new Quote("quote", "author"));
    }
}
