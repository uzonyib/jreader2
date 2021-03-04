package jreader2.service.impl;

import jreader2.dao.GroupDao;
import jreader2.dao.SubscriptionDao;
import jreader2.domain.Group;
import jreader2.domain.Subscription;
import jreader2.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupDao groupDao;
    private final SubscriptionDao subscriptionDao;

    @Override
    public Group create(String email, Group group) {
        long id = groupDao.create(email, group);
        return groupDao.find(email, id).get();
    }

    @Override
    public List<Group> list(String email) {
        List<Group> groups = groupDao.listAll(email);
        List<Subscription> subscriptions = subscriptionDao.listAll(email);
        groups.forEach(group -> group.setSubscriptions(
                subscriptions.stream().filter(
                        subscription -> subscription.getGroupId() == group.getId()
                ).collect(Collectors.toList())));
        return groups;
    }

}
