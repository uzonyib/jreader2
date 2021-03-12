package jreader2.service;

import jreader2.domain.Role;
import jreader2.domain.User;

import java.util.List;

public interface UserService {

    boolean isAuthorized(String email);

    List<User> list(Role role);

}
