package jreader2.dao;

import jreader2.domain.Feed;

import java.util.List;
import java.util.Optional;

public interface FeedDao {

    void create(Feed feed);

    Optional<Feed> find(String url);

    List<Feed> listAll();

}
