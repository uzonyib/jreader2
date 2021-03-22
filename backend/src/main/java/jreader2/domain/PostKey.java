package jreader2.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostKey {

    private String ownerEmail;
    private long groupId;
    private long subscriptionId;
    private String uri;

}
