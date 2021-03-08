package jreader2.dao;

import jreader2.domain.Group;

import java.util.List;
import java.util.Optional;

public interface GroupDao {

    long create(String email, Group group);

    void delete(String email, long id);

    Optional<Group> find(String email, long id);

    List<Group> listAll(String email);

}
