package jreader2.dao;

import jreader2.domain.User;

import java.util.Optional;

public interface UserDao {

    void create(User user);

    Optional<User> find(String email);

}
