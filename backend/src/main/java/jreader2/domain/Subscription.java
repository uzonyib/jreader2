package jreader2.domain;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
public class Subscription {

    private long id;
    private long groupId;
    private String url;
    private String name;
    private long rank;

}
