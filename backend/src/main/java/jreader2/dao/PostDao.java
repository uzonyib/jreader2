package jreader2.dao;

import jreader2.domain.Post;
import jreader2.domain.Subscription;

import java.util.List;

public interface PostDao {

    void create(Subscription subscription, Post post);

    List<Post> listAll(String email);

}
