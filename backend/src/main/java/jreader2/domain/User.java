package jreader2.domain;

import lombok.Value;

@Value
public class User {

    private final String email;
    private final Role role;

}
