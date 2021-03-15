package jreader2.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class PostFilter {

    private final String email;
    private Optional<Long> groupId;
    private Optional<Long> subscriptionId;
    private int limit;

}
