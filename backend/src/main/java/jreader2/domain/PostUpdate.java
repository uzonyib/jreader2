package jreader2.domain;

import lombok.Data;

@Data
public class PostUpdate {

    private long groupId;
    private long subscriptionId;
    private String uri;
    private Boolean read;
    private Boolean bookmarked;

}
