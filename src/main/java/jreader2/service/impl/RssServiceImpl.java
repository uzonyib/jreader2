package jreader2.service.impl;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import jreader2.service.RssService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class RssServiceImpl implements RssService {

    private final WebClient webClient;

    @Override
    public void fetch(String url) {
        webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(InputStreamResource.class)
                .flatMap(response -> {
                    SyndFeedInput input = new SyndFeedInput();
                    try {
                        SyndFeed feed = input.build(new XmlReader(response.getInputStream()));
                        log.info("Fetched {} entries from {}.", feed.getEntries().size(), url);
                        return Flux.fromIterable(feed.getEntries());
                    } catch (FeedException | IOException e) {
                        log.error("Failed to fetch {}", e);
                        return Flux.empty();
                    }
                })
                .doOnError(error -> log.error("Error while fetching {}", url, error))
                .subscribe();
    }

}
