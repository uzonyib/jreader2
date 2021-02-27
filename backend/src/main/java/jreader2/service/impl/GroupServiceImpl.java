package jreader2.service.impl;

import jreader2.dao.GroupDao;
import jreader2.domain.Group;
import jreader2.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupDao groupDao;

    @Override
    public Group create(String email, Group group) {
        long id = groupDao.create(email, group);
        return groupDao.find(email, id).get();
    }

    @Override
    public List<Group> list(String email) {
        return groupDao.listAll(email);
    }

}
