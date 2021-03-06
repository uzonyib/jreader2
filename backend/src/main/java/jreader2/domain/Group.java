package jreader2.domain;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Jacksonized
@Builder
public class Group {

    private long id;
    private String name;
    private long rank;
    private List<Subscription> subscriptions;

}
