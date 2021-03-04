package jreader2.dao.impl;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.StructuredQuery;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import jreader2.dao.SubscriptionDao;
import jreader2.domain.Subscription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class SubscriptionDaoImpl implements SubscriptionDao {

    private final Datastore datastore;
    private final KeyFactory keyFactory;

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
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(entities, Spliterator.ORDERED), false)
                .map(this::toSubscription)
                .collect(Collectors.toList());
    }

    private Subscription toSubscription(Entity subscription) {
        return Subscription.builder()
                .id(subscription.getKey().getId())
                .groupId(subscription.getKey().getAncestors().get(1).getId())
                .url(subscription.getString("url"))
                .name(subscription.getString("name"))
                .rank(subscription.getLong("rank"))
                .build();
    }

}
