package jreader2.converter;

import jreader2.domain.FeedEntry;
import jreader2.domain.Post;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FeedEntryToPostConverter implements Converter<FeedEntry, Post> {

    @Override
    public Post convert(FeedEntry entry) {
        return Post.builder()
                .uri(entry.getUri())
                .url(entry.getUrl())
                .title(entry.getTitle())
                .description(entry.getDescription())
                .author(entry.getAuthor())
                .publishDate(entry.getPublishDate())
                .read(false)
                .bookmarked(false)
                .build();
    }

}
