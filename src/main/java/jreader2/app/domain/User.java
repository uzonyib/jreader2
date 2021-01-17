package jreader2.app.domain;

import lombok.Value;

@Value
public class User {

    private final String email;
    private final Role role;

}
