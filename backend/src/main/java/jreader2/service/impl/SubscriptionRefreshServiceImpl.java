package jreader2.service.impl;

import jreader2.dao.PostDao;
import jreader2.dao.SubscriptionDao;
import jreader2.domain.FeedEntry;
import jreader2.domain.Post;
import jreader2.domain.Subscription;
import jreader2.service.SubscriptionRefreshService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.ZonedDateTime;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionRefreshServiceImpl implements SubscriptionRefreshService {

    private final SubscriptionDao subscriptionDao;
    private final PostDao postDao;
    private final ConversionService conversionService;

    @Override
    public void refresh(String feedUrl, Flux<FeedEntry> entries) {
        List<Subscription> subscriptions = subscriptionDao.listForUrl(feedUrl);

        ZonedDateTime now = ZonedDateTime.now();
        entries.filter(entry -> nonNull(entry.getUri()))
                .filter(entry -> nonNull(entry.getPublishDate()))
                .filter(entry -> entry.getPublishDate().isBefore(now))
                .doOnNext(entry -> processEntry(entry, subscriptions))
                .reduce((entry1, entry2) ->
                        entry1.getPublishDate().isAfter(entry2.getPublishDate()) ? entry1 : entry2)
                .doOnNext(entry -> updateSubscriptions(subscriptions, entry.getPublishDate()))
                .subscribe();
    }

    private void processEntry(FeedEntry entry, List<Subscription> subscriptions) {
        subscriptions.forEach(subscription -> {
            if (shouldProcessPostWithDateForSubscription(entry.getPublishDate(), subscription)) {
                processEntry(entry, subscription);
            }
        });
    }

    private void processEntry(FeedEntry entry, Subscription subscription) {
        log.info("Saving new post (with publish date {}) for subscription {} (last updated {}) of user {}",
                entry.getPublishDate(), subscription.getName(), subscription.getLastUpdateDate(),
                subscription.getOwnerEmail());
        Post post = conversionService.convert(entry, Post.class);
        postDao.create(subscription, post);
    }

    private void updateSubscriptions(List<Subscription> subscriptions, ZonedDateTime lastUpdateDate) {
        subscriptions.forEach(subscription -> {
            if (shouldProcessPostWithDateForSubscription(lastUpdateDate, subscription)) {
                log.info("Subscription {} of user {} updated as of {}",
                        subscription.getName(), subscription.getOwnerEmail(), lastUpdateDate);
                subscription.setLastUpdateDate(lastUpdateDate);
                subscriptionDao.update(subscription);
            }
        });
    }

    private boolean shouldProcessPostWithDateForSubscription(ZonedDateTime lastUpdateDate, Subscription subscription) {
        return isNull(subscription.getLastUpdateDate()) || lastUpdateDate.isAfter(subscription.getLastUpdateDate());
    }

}
