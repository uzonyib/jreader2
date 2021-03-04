package jreader2.service.impl;

import jreader2.dao.FeedDao;
import jreader2.domain.Feed;
import jreader2.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedDao feedDao;

    @Override
    public void createIfNotExists(String url) {
        if (feedDao.find(url).isEmpty()) {
            log.info("Registering new feed {}", url);
            feedDao.create(new Feed(url));
        }
    }
}
