package jreader2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class Post {

    @JsonIgnore
    private String ownerEmail;
    private long groupId;
    private long subscriptionId;
    private String uri;
    private String url;
    private String title;
    private String description;
    private String author;
    private ZonedDateTime publishDate;
    private boolean read;
    private boolean bookmarked;

}
