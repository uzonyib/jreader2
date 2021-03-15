package jreader2.web.service.impl;

import jreader2.dao.PostDao;
import jreader2.domain.Post;
import jreader2.domain.PostFilter;
import jreader2.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostDao postDao;

    @Override
    public List<Post> list(PostFilter filter) {
        return postDao.listAll(filter);
    }

}
