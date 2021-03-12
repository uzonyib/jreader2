package jreader2.dao.impl;

import com.google.cloud.datastore.*;
import jreader2.dao.UserDao;
import jreader2.domain.Group;
import jreader2.domain.Role;
import jreader2.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final Datastore datastore;
    private final KeyFactory keyFactory;

    @Override
    public void create(User user) {
        datastore.put(Entity.newBuilder(keyFactory.createUserKey(user.getEmail()))
                .set("role", user.getRole().name())
                .build());
    }

    @Override
    public Optional<User> find(String email) {
        return Optional.ofNullable(datastore.get(keyFactory.createUserKey(email))).map(e -> toUser(e));
    }

    @Override
    public List<User> list(Role role) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("User")
                .setFilter(StructuredQuery.PropertyFilter.eq("role", role.name()))
                .build();
        Iterator<Entity> entities = datastore.run(query);
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(entities, Spliterator.ORDERED), false)
                .map(this::toUser)
                .collect(Collectors.toList());
    }

    private User toUser(Entity e) {
        return new User(e.getKey().getName(), Role.valueOf(e.getString("role")));
    }

}
