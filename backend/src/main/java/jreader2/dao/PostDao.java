package jreader2.dao;

import jreader2.domain.Post;
import jreader2.domain.Subscription;

import java.util.List;

public interface PostDao {

    void create(Subscription subscription, Post post);

    void delete(List<Post> posts);

    List<Post> listAll(String email);

    List<Post> list(Subscription subscription, int postsToKeep);

}
