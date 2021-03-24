package jreader2.service.impl;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import jreader2.domain.FeedEntry;
import jreader2.service.RssService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.ZonedDateTime;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class RssServiceImpl implements RssService {

    private final WebClient webClient;
    private final ConversionService conversionService;

    @Override
    public Flux<FeedEntry> fetch(String url) {
        return webClient
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
                        log.error("Failed to fetch " + url, e);
                        return Flux.empty();
                    }
                })
                .doOnError(error -> log.error("Error while fetching {}", url, error))
                .map(this::toFeedEntry);
    }

    private FeedEntry toFeedEntry(SyndEntry syndEntry) {
        return FeedEntry.builder()
                .uri(syndEntry.getUri())
                .url(syndEntry.getLink())
                .title(syndEntry.getTitle())
                .description(ofNullable(syndEntry.getDescription()).map(SyndContent::getValue).orElse(null))
                .author(syndEntry.getAuthor())
                .publishDate(ofNullable(syndEntry.getPublishedDate())
                        .map(publishDate -> conversionService.convert(publishDate, ZonedDateTime.class))
                        .orElse(null))
                .build();
    }

}
