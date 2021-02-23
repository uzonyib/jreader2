package jreader2.service.impl;

import jreader2.dao.FeedDao;
import jreader2.domain.Feed;
import jreader2.service.CronService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CronServiceImpl implements CronService {

    private final FeedDao feedDao;

    @Override
    public List<String> listAll() {
        return feedDao.listAll()
                .stream()
                .map(Feed::getUrl)
                .collect(Collectors.toList());
    }

}
