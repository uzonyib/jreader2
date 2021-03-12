package jreader2.service.impl;

import jreader2.dao.PostDao;
import jreader2.domain.Post;
import jreader2.domain.Subscription;
import jreader2.service.PostCleanupService;
import jreader2.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostCleanupServiceImpl implements PostCleanupService {

    private static final int POSTS_TO_KEEP = 50;

    private final SubscriptionService subscriptionService;
    private final PostDao postDao;

    @Override
    public void cleanupFor(String email) {
        List<Subscription> subscriptions = subscriptionService.list(email);
        subscriptions.forEach(subscription -> {
            List<Post> postsToDelete = postDao.list(subscription, POSTS_TO_KEEP);
            log.info("Deleting {} posts from {} for user {}", postsToDelete.size(), subscription.getUrl(), email);
            postDao.delete(postsToDelete);
        });
    }

}
