package jreader2.service.impl;

import jreader2.dao.UserDao;
import jreader2.domain.Role;
import jreader2.domain.User;
import jreader2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static jreader2.domain.Role.NONE;
import static jreader2.domain.Role.USER;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    
    @Override
    public boolean isAuthorized(String email) {
        Optional<User> user = userDao.find(email);
        if (user.isEmpty()) {
            register(email);
            return false;
        }
        log.info("User found with email {} and role {}.", email, user.get().getRole());
        return user.get().getRole() == USER;
    }

    @Override
    public List<User> list(Role role) {
        return userDao.list(role);
    }

    private void register(String email) {
        log.info("Registering user with email {}.", email);
        userDao.create(new User(email, NONE));
    }

}
