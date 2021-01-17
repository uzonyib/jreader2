package jreader2.app.dao.impl;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import jreader2.app.dao.UserDao;
import jreader2.app.domain.Role;
import jreader2.app.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final Datastore datastore;

    private Key getKey(String email) {
        return datastore.newKeyFactory().setKind("User").newKey(email);
    }

    @Override
    public void create(User user) {
        datastore.put(Entity.newBuilder(getKey(user.getEmail()))
                .set("role", user.getRole().name())
                .build());
    }

    @Override
    public Optional<User> find(String email) {
        return Optional.ofNullable(datastore.get(getKey(email)))
                .map(e -> new User(e.getKey().getName(), Role.valueOf(e.getString("role"))));
    }

}
