package jreader2.dao.impl;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.StructuredQuery;
import jreader2.dao.PostDao;
import jreader2.domain.Post;
import jreader2.domain.Subscription;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class PostDaoImpl implements PostDao {

    private final Datastore datastore;
    private final KeyFactory keyFactory;
    private final ConversionService conversionService;

    @Override
    public void create(Subscription subscription, Post post) {
        datastore.put(Entity.newBuilder(keyFactory.createPostKey(subscription, post.getUri()))
                .set("url", post.getUrl())
                .set("title", post.getTitle())
                .set("description", post.getDescription())
                .set("author", post.getAuthor())
                .set("publishDate", conversionService.convert(post.getPublishDate(), Timestamp.class))
                .set("read", post.isRead())
                .set("bookmarked", post.isBookmarked())
                .build());
    }

    @Override
    public List<Post> listAll(String email) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Post")
                .setFilter(StructuredQuery.PropertyFilter.hasAncestor(keyFactory.createUserKey(email)))
                .setOrderBy(StructuredQuery.OrderBy.asc("publishDate"))
                .build();
        Iterator<Entity> entities = datastore.run(query);
        return toStream(entities)
                .map(this::toPost)
                .collect(Collectors.toList());
    }

    private Stream<Entity> toStream(Iterator<Entity> entities) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(entities, Spliterator.ORDERED), false);
    }

    private Post toPost(Entity entity) {
        return Post.builder()
                .uri(entity.getKey().getName())
                .url(entity.getString("url"))
                .title(entity.getString("title"))
                .description(entity.getString("description"))
                .author(entity.getString("author"))
                .publishDate(conversionService.convert(entity.getTimestamp("publishDate"), ZonedDateTime.class))
                .read(entity.getBoolean("read"))
                .bookmarked(entity.getBoolean("bookmarked"))
                .build();
    }

}
