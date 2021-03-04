package jreader2.dao.impl;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import jreader2.dao.FeedDao;
import jreader2.domain.Feed;
import jreader2.domain.Role;
import jreader2.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class FeedDaoImpl implements FeedDao {

    private final Datastore datastore;
    private final KeyFactory keyFactory;

    @Override
    public void create(Feed feed) {
        datastore.put(Entity.newBuilder(keyFactory.createFeedKey(feed.getUrl())).build());
    }

    @Override
    public Optional<Feed> find(String url) {
        return Optional.ofNullable(datastore.get(keyFactory.createFeedKey(url)))
                .map(e -> new Feed(e.getKey().getName()));
    }

    @Override
    public List<Feed> listAll() {
        Query<Entity> query = Query.newEntityQueryBuilder().setKind("Feed").build();
        Iterator<Entity> entities = datastore.run(query);
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(entities, Spliterator.ORDERED), false)
                .map(e -> new Feed(e.getKey().getName()))
                .collect(Collectors.toList());
    }

}
