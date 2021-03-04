package jreader2.dao.impl;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.PathElement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KeyFactory {

    public static final String USER_KIND = "User";
    public static final String GROUP_KIND = "Group";
    public static final String SUBSCRIPTION_KIND = "Subscription";
    public static final String FEED_KIND = "Feed";

    private final Datastore datastore;

    public Key createUserKey(String email) {
        return datastore.newKeyFactory().setKind(USER_KIND).newKey(email);
    }

    public IncompleteKey createGroupKey(String email) {
        return datastore.newKeyFactory()
                .addAncestor(createUserAncestor(email))
                .setKind(GROUP_KIND)
                .newKey();
    }

    public Key createGroupKey(String email, long id) {
        return datastore.newKeyFactory()
                .addAncestor(createUserAncestor(email))
                .setKind(GROUP_KIND)
                .newKey(id);
    }

    public IncompleteKey createSubscriptionKey(String email, long groupId) {
        return datastore.newKeyFactory()
                .addAncestors(createUserAncestor(email), createGroupAncestor(groupId))
                .setKind(SUBSCRIPTION_KIND)
                .newKey();
    }

    public Key createSubscriptionKey(String email, long groupId, long id) {
        return datastore.newKeyFactory()
                .addAncestors(createUserAncestor(email), createGroupAncestor(groupId))
                .setKind(SUBSCRIPTION_KIND)
                .newKey(id);
    }

    public Key createFeedKey(String url) {
        return datastore.newKeyFactory().setKind(FEED_KIND).newKey(url);
    }

    private PathElement createUserAncestor(String email) {
        return PathElement.of(USER_KIND, email);
    }

    private PathElement createGroupAncestor(long groupId) {
        return PathElement.of(GROUP_KIND, groupId);
    }

}
