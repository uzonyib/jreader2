package jreader2.web.service;

import jreader2.domain.Post;

import java.util.List;

public interface PostService {

    List<Post> list(String email);

}
