package jreader2.dao.impl;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import jreader2.dao.GroupDao;
import jreader2.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.google.cloud.datastore.StructuredQuery.OrderBy;
import static com.google.cloud.datastore.StructuredQuery.PropertyFilter;

@Repository
@RequiredArgsConstructor
public class GroupDaoImpl implements GroupDao {

    private final Datastore datastore;
    private final KeyFactory keyFactory;

    @Override
    public long create(String email, Group group) {
        return datastore.put(Entity.newBuilder(keyFactory.createGroupKey(email))
                .set("name", group.getName())
                .set("rank", group.getRank())
                .build()
        ).getKey().getId();
    }

    @Override
    public Optional<Group> find(String email, long id) {
        return Optional.ofNullable(datastore.get(keyFactory.createGroupKey(email, id)))
                .map(this::toGroup);
    }

    @Override
    public List<Group> listAll(String email) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Group")
                .setFilter(PropertyFilter.hasAncestor(keyFactory.createUserKey(email)))
                .setOrderBy(OrderBy.asc("rank"))
                .build();
        Iterator<Entity> entities = datastore.run(query);
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(entities, Spliterator.ORDERED), false)
                .map(this::toGroup)
                .collect(Collectors.toList());
    }

    private Group toGroup(Entity group) {
        return Group.builder()
                .id(group.getKey().getId())
                .name(group.getString("name"))
                .rank(group.getLong("rank"))
                .build();
    }

}
