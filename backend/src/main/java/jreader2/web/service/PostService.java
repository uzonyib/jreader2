package jreader2.web.service;

import jreader2.domain.Post;
import jreader2.domain.PostFilter;
import jreader2.domain.PostUpdate;

import java.util.List;

public interface PostService {

    List<Post> list(PostFilter filter);

    void update(String email, List<PostUpdate> updates);
}
