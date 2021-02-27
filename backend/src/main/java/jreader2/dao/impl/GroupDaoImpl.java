package jreader2.dao.impl;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.StructuredQuery;
import jreader2.dao.GroupDao;
import jreader2.domain.Group;
import jreader2.domain.Role;
import jreader2.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class GroupDaoImpl implements GroupDao {

    private final Datastore datastore;
    private final KeyFactory keyFactory;

    @Override
    public long create(String email, Group group) {
        return datastore.put(Entity.newBuilder(keyFactory.createGroupKey(email))
                .set("name", group.getName())
                .build()
        ).getKey().getId();
    }

    @Override
    public Optional<Group> find(String email, long id) {
        return Optional.ofNullable(datastore.get(keyFactory.createGroupKey(email, id)))
                .map(g -> new Group(g.getKey().getId(), g.getString("name")));
    }

    @Override
    public List<Group> listAll(String email) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Group")
                .setFilter(StructuredQuery.PropertyFilter.hasAncestor(
                        keyFactory.createUserKey(email)
                ))
                .build();
        Iterator<Entity> entities = datastore.run(query);
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(entities, Spliterator.ORDERED), false)
                .map(g -> new Group(g.getKey().getId(), g.getString("name")))
                .collect(Collectors.toList());
    }
}
