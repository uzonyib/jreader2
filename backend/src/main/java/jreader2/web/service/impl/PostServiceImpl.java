package jreader2.web.service.impl;

import jreader2.dao.PostDao;
import jreader2.domain.Post;
import jreader2.domain.PostFilter;
import jreader2.domain.PostKey;
import jreader2.domain.PostUpdate;
import jreader2.web.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostDao postDao;

    @Override
    public List<Post> list(PostFilter filter) {
        return postDao.listAll(filter);
    }

    @Override
    public void update(String email, List<PostUpdate> updates) {
        PostKey[] keys = updates.stream().map(update -> PostKey.builder()
                .ownerEmail(email)
                .groupId(update.getGroupId())
                .subscriptionId(update.getSubscriptionId())
                .uri(update.getUri())
                .build())
                .toArray(PostKey[]::new);
        List<Post> posts = postDao.list(keys);
        posts.forEach(post -> {
            PostUpdate update = findUpdateByKey(post, updates);
            merge(post, update);
        });
        postDao.update(posts.toArray(new Post[posts.size()]));
    }

    private PostUpdate findUpdateByKey(Post post, List<PostUpdate> updates) {
        List<PostUpdate> matches = updates.stream().filter(update -> update.getGroupId() == post.getGroupId()
                && update.getSubscriptionId() == post.getSubscriptionId()
                && update.getUri().equals(post.getUri()))
                .collect(Collectors.toList());
        if (matches.isEmpty() || matches.size() > 1) {
            log.warn("Post with URI {} not found in group {} and subscription", post.getUri(), post.getGroupId(),
                    post.getSubscriptionId());
            throw new IllegalArgumentException("Post not found.");
        }
        return matches.get(0);
    }

    private void merge(Post post, PostUpdate update) {
        if (nonNull(update.getRead())) {
            post.setRead(update.getRead());
        }
        if (nonNull(update.getBookmarked())) {
            post.setBookmarked(update.getBookmarked());
        }
    }

}
