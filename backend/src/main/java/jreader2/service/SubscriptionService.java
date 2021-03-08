package jreader2.service;

import jreader2.domain.Subscription;

public interface SubscriptionService {

    Subscription subscribe(String email, long groupId, Subscription subscription);

    void unsubscribe(String email, long groupId, long subscriptionId);

}
