package jreader2.service;

import jreader2.domain.FeedEntry;
import reactor.core.publisher.Flux;

public interface RssService {

    Flux<FeedEntry> fetch(String url);

}
