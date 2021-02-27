package jreader2.dao.impl;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.PathElement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KeyFactory {

    private final Datastore datastore;

    public Key createUserKey(String email) {
        return datastore.newKeyFactory().setKind("User").newKey(email);
    }

    public IncompleteKey createGroupKey(String email) {
        return datastore.newKeyFactory().addAncestor(PathElement.of("User", email)).setKind("Group").newKey();
    }

    public Key createGroupKey(String email, long id) {
        return datastore.newKeyFactory().addAncestor(PathElement.of("User", email)).setKind("Group").newKey(id);
    }

}
