package jreader2.web.service;

import jreader2.domain.Post;
import jreader2.domain.PostFilter;

import java.util.List;

public interface PostService {

    List<Post> list(PostFilter filter);

}
