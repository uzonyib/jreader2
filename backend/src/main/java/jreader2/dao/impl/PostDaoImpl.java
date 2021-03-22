package jreader2.dao.impl;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import jreader2.dao.PostDao;
import jreader2.domain.Post;
import jreader2.domain.PostFilter;
import jreader2.domain.PostKey;
import jreader2.domain.Subscription;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Objects.nonNull;

@Repository
@RequiredArgsConstructor
public class PostDaoImpl implements PostDao {

    private final Datastore datastore;
    private final KeyFactory keyFactory;
    private final ConversionService conversionService;

    @Override
    public void create(Subscription subscription, Post post) {
        datastore.put(toEntity(keyFactory.createPostKey(subscription, post.getUri()), post));
    }

    @Override
    public void update(Post... posts) {
        datastore.update(Arrays.stream(posts).map(this::toEntity).toArray(Entity[]::new));
    }

    @Override
    public void delete(List<Post> posts) {
        datastore.delete(posts.stream().map(post -> keyFactory.createPostKey(
                post.getOwnerEmail(), post.getGroupId(), post.getSubscriptionId(), post.getUri()))
                .toArray(Key[]::new));
    }

    @Override
    public List<Post> list(PostKey... keys) {
        List<Key> entityKeys = Arrays.stream(keys).map(key -> keyFactory.createPostKey(key.getOwnerEmail(),
                key.getGroupId(), key.getSubscriptionId(), key.getUri())).collect(Collectors.toList());
        Iterator<Entity> entities = datastore.get(entityKeys);
        return toStream(entities)
                .map(this::toPost)
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> listAll(PostFilter filter) {
        Query query = Query.newEntityQueryBuilder()
                .setKind("Post")
                .setFilter(getAncestorFilter(filter))
                .setOrderBy(filter.isAscendingOrder() ?
                        StructuredQuery.OrderBy.asc("publishDate") : StructuredQuery.OrderBy.desc("publishDate"))
                .setLimit(filter.getLimit())
                .build();
        Iterator<Entity> entities = datastore.run(query);
        return toStream(entities)
                .map(this::toPost)
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> list(Subscription subscription, int postsToKeep) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Post")
                .setFilter(StructuredQuery.PropertyFilter.hasAncestor(keyFactory.createSubscriptionKey(
                        subscription.getOwnerEmail(), subscription.getGroupId(), subscription.getId())))
                .setOrderBy(StructuredQuery.OrderBy.desc("publishDate"))
                .setOffset(postsToKeep)
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
                .ownerEmail(entity.getKey().getAncestors().get(0).getName())
                .groupId(entity.getKey().getAncestors().get(1).getId())
                .subscriptionId(entity.getKey().getAncestors().get(2).getId())
                .uri(entity.getKey().getName())
                .url(entity.getString("url"))
                .title(entity.getString("title"))
                .description(entity.contains("description") ? entity.getString("description") : null)
                .author(entity.contains("author") ? entity.getString("author") : null)
                .publishDate(conversionService.convert(entity.getTimestamp("publishDate"), ZonedDateTime.class))
                .read(entity.getBoolean("read"))
                .bookmarked(entity.getBoolean("bookmarked"))
                .build();
    }

    private Entity toEntity(Post post) {
        return toEntity(keyFactory.createPostKey(post.getOwnerEmail(), post.getGroupId(), post.getSubscriptionId(),
                post.getUri()), post);
    }

    private Entity toEntity(Key key, Post post) {
        Entity.Builder builder = Entity.newBuilder(key)
                .set("url", post.getUrl())
                .set("title", post.getTitle())
                .set("publishDate", conversionService.convert(post.getPublishDate(), Timestamp.class))
                .set("read", post.isRead())
                .set("bookmarked", post.isBookmarked());
        if (nonNull(post.getDescription()) && !post.getDescription().isBlank()) {
            builder.set("description", post.getDescription());
        }
        if (nonNull(post.getAuthor()) && !post.getAuthor().isBlank()) {
            builder.set("author", post.getAuthor());
        }
        return builder.build();
    }

    private StructuredQuery.PropertyFilter getAncestorFilter(PostFilter filter) {
        if (filter.getSubscriptionId().isPresent()) {
            return StructuredQuery.PropertyFilter.hasAncestor(keyFactory.createSubscriptionKey(
                    filter.getEmail(), filter.getGroupId().get(), filter.getSubscriptionId().get()));
        }
        if (filter.getGroupId().isPresent()) {
            return StructuredQuery.PropertyFilter.hasAncestor(keyFactory.createGroupKey(
                    filter.getEmail(), filter.getGroupId().get()));
        }
        return StructuredQuery.PropertyFilter.hasAncestor(keyFactory.createUserKey(filter.getEmail()));
    }

}
