package jreader2.dao.impl;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import jreader2.dao.SubscriptionDao;
import jreader2.domain.Subscription;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class SubscriptionDaoImpl implements SubscriptionDao {

    private final Datastore datastore;
    private final KeyFactory keyFactory;
    private final ConversionService conversionService;

    @Override
    public long create(String email, long groupId, Subscription subscription) {
        return datastore.put(Entity.newBuilder(keyFactory.createSubscriptionKey(email, groupId))
                .set("url", subscription.getUrl())
                .set("name", subscription.getName())
                .set("rank", subscription.getRank())
                .build()
        ).getKey().getId();
    }

    @Override
    public void update(Subscription subscription) {
        datastore.update(Entity.newBuilder(
                keyFactory.createSubscriptionKey(subscription.getOwnerEmail(), subscription.getGroupId(), subscription.getId()))
                .set("url", subscription.getUrl())
                .set("name", subscription.getName())
                .set("rank", subscription.getRank())
                .set("lastUpdateDate", conversionService.convert(subscription.getLastUpdateDate(), Timestamp.class))
                .build());
    }

    @Override
    public void delete(String email, long groupId, long id) {
        datastore.delete(keyFactory.createSubscriptionKey(email, groupId, id));
    }

    @Override
    public Optional<Subscription> find(String email, long groupId, long id) {
        return Optional.ofNullable(datastore.get(keyFactory.createSubscriptionKey(email, groupId, id)))
                .map(this::toSubscription);
    }

    @Override
    public List<Subscription> listAll(String email) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Subscription")
                .setFilter(PropertyFilter.hasAncestor(keyFactory.createUserKey(email)))
                .setOrderBy(StructuredQuery.OrderBy.asc("rank"))
                .build();
        Iterator<Entity> entities = datastore.run(query);
        return toStream(entities)
                .map(this::toSubscription)
                .collect(Collectors.toList());
    }

    @Override
    public List<Subscription> listAll(String email, long groupId) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Subscription")
                .setFilter(PropertyFilter.hasAncestor(keyFactory.createGroupKey(email, groupId)))
                .setOrderBy(StructuredQuery.OrderBy.asc("rank"))
                .build();
        QueryResults<Entity> entities = datastore.run(query);
        Cursor cursorAfter = entities.getCursorAfter();
        return toStream(entities)
                .map(this::toSubscription)
                .collect(Collectors.toList());
    }

    @Override
    public List<Subscription> listForUrl(String url) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Subscription")
                .setFilter(PropertyFilter.eq("url", url))
                .build();
        Iterator<Entity> entities = datastore.run(query);
        return toStream(entities)
                .map(this::toSubscription)
                .collect(Collectors.toList());
    }

    private Stream<Entity> toStream(Iterator<Entity> entities) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(entities, Spliterator.ORDERED), false);
    }

    private Subscription toSubscription(Entity subscription) {
        return Subscription.builder()
                .id(subscription.getKey().getId())
                .groupId(subscription.getKey().getAncestors().get(1).getId())
                .ownerEmail(subscription.getKey().getAncestors().get(0).getName())
                .url(subscription.getString("url"))
                .name(subscription.getString("name"))
                .rank(subscription.getLong("rank"))
                .lastUpdateDate(getLastUpdateDate(subscription))
                .build();
    }

    private ZonedDateTime getLastUpdateDate(Entity subscription) {
        String key = "lastUpdateDate";
        return subscription.contains(key) ?
                conversionService.convert(subscription.getTimestamp(key), ZonedDateTime.class) : null;
    }

}
