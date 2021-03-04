package jreader2.service.impl;

import jreader2.dao.SubscriptionDao;
import jreader2.domain.Subscription;
import jreader2.service.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionDao subscriptionDao;

    @Override
    public Subscription subscribe(String email, long groupId, Subscription subscription) {
        long subscriptionId = subscriptionDao.create(email, groupId, subscription);
        return subscriptionDao.find(email, groupId, subscriptionId).get();
    }

}
