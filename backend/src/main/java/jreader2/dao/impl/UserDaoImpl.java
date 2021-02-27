package jreader2.dao.impl;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import jreader2.dao.UserDao;
import jreader2.domain.Role;
import jreader2.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
        return Optional.ofNullable(datastore.get(keyFactory.createUserKey(email)))
                .map(e -> new User(e.getKey().getName(), Role.valueOf(e.getString("role"))));
    }

}
