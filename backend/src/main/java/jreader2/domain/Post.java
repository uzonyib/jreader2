package jreader2.domain;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class Post {

    private String uri;
    private String url;
    private String title;
    private String description;
    private String author;
    private ZonedDateTime publishDate;
    private boolean read;
    private boolean bookmarked;

}
