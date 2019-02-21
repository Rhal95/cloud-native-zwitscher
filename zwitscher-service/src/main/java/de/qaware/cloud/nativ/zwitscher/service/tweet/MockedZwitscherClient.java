package de.qaware.cloud.nativ.zwitscher.service.tweet;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MockedZwitscherClient implements ZwitscherClient {

    @Override
    public Collection<ZwitscherMessage> search(String q, int pageSize) {
        try{
           Thread.sleep(100);
        } catch (InterruptedException ignored) {}
    return Stream.iterate(1, i->i+1).limit(pageSize).map(i-> new ZwitscherMessage("msg " + i)).collect(Collectors.toList());
    }
}
