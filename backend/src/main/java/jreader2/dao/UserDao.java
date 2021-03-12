package jreader2.dao;

import jreader2.domain.Role;
import jreader2.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    void create(User user);

    Optional<User> find(String email);

    List<User> list(Role role);

}
