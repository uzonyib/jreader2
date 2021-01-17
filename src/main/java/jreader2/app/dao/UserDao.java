package jreader2.app.dao;

import jreader2.app.domain.User;

import java.util.Optional;

public interface UserDao {

    void create(User user);

    Optional<User> find(String email);

}
