package jreader2.dao;

import jreader2.domain.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionDao {

    long create(String email, long groupId, Subscription subscription);

    Optional<Subscription> find(String email, long groupId, long id);

    List<Subscription> listAll(String email);

}
