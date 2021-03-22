package jreader2.dao;

import jreader2.domain.Post;
import jreader2.domain.PostFilter;
import jreader2.domain.PostKey;
import jreader2.domain.Subscription;

import java.util.List;

public interface PostDao {

    void create(Subscription subscription, Post post);

    void update(Post... posts);

    void delete(List<Post> posts);

    List<Post> list(PostKey... keys);

    List<Post> listAll(PostFilter filter);

    List<Post> list(Subscription subscription, int postsToKeep);

}
