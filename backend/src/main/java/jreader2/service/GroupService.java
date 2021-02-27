package jreader2.service;

import jreader2.domain.Group;

import java.util.List;

public interface GroupService {

    Group create(String email, Group group);

    List<Group> list(String email);

}
