package jreader2.dao.impl;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import jreader2.dao.FeedDao;
import jreader2.domain.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class FeedDaoImpl implements FeedDao {

    private final Datastore datastore;

    @Override
    public List<Feed> listAll() {
        Query<Entity> query = Query.newEntityQueryBuilder().setKind("Feed").build();
        Iterator<Entity> entities = datastore.run(query);
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(entities, Spliterator.ORDERED), false)
                .map(e -> new Feed(e.getKey().getName()))
                .collect(Collectors.toList());
    }

}
