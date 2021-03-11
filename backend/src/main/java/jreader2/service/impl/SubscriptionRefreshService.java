package jreader2.service.impl;

import jreader2.domain.FeedEntry;
import reactor.core.publisher.Flux;

public interface SubscriptionRefreshService {

    void refresh(String feedUrl, Flux<FeedEntry> entries);

}
