package jreader2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;

@Data
@Jacksonized
@Builder
public class Subscription {

    @JsonIgnore
    private String ownerEmail;
    private long groupId;
    private long id;
    private String url;
    private String name;
    private long rank;
    private ZonedDateTime lastUpdateDate;

}
